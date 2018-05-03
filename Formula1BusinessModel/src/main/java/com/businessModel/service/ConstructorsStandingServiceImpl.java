package com.businessModel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.businessModel.converter.RawDataConverter;
import com.businessModel.model.Constructor;
import com.businessModel.repository.ConstructorRepository;
import com.businessModel.utils.DataValidator;
import com.businessModel.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
@Service
public class ConstructorsStandingServiceImpl implements ConstructorsStandingService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ConstructorRepository constructorRepository;

    /**
     * Method return an entity found in Redis
     */
    @Override
    public Constructor getByTitle(String title) throws IOException {
        return constructorRepository.getConstructorByTitle(title);
    }

    /**
     * Method updates Redis data for particular constructor with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether constructor already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    @Override
    public void updateStandingsWithData(String constructorId, String constructorsData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(constructorsData);

        DataValidator.validateConstructorData(json);

        Constructor entity = getByTitle(constructorId);
        if (entity != null) {
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            constructorRepository.saveOrUpdate(entity);
        } else {
            entity = new Constructor(constructorId);
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            constructorRepository.saveOrUpdate(entity);
        }
    }

    @Override
    public void update(Constructor constructor) {
        constructorRepository.saveOrUpdate(constructor);
    }

    /**
     * Method updates Redis data for a bench of constructors with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether constructor already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     *
     * @param constructorList
     */
    @Override
    public void updateStandingsWithData(List<Constructor> constructorList) {
        constructorList.forEach(constructor -> constructorRepository.saveOrUpdate(constructor));
    }

    @Override
    public List<Constructor> getAllStandings() {
        Set<String> allKeys = redisTemplate.keys(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + "*");
        List<Object> fetchedData = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            Iterator iter = allKeys.iterator();
            while (iter.hasNext()) {
                connection.get(((String) iter.next()).getBytes());
            }
            return null;
        });
        return RawDataConverter.convertToConstructors(fetchedData);
    }
}
