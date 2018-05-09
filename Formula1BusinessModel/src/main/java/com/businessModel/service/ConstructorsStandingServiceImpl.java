package com.businessModel.service;

import com.businessModel.model.Constructor;
import com.businessModel.repository.ConstructorRepository;
import com.businessModel.utils.DataValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
@Service
public class ConstructorsStandingServiceImpl implements ConstructorsStandingService {
    private final Logger logger = LoggerFactory.getLogger(ConstructorsStandingServiceImpl.class);
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
    public void updateStandingsWithData(String constructorId, String constructorsData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(constructorsData);

            DataValidator.validateConstructorData(json);

            Constructor entity = getByTitle(constructorId);
            if (entity != null) {
                entity.setPoints(Short.parseShort(json.get("points").toString()));
                constructorRepository.saveOrUpdate(entity);
            } else {
                entity = new Constructor(constructorId);
                entity.setPoints(Short.parseShort(json.get("points").toString()));
                constructorRepository.saveOrUpdate(entity);
            }
        } catch (Exception e) {
            logger.error("Exception during updating constructor: " + constructorId + " with data: " + constructorsData, e);
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
    public Collection<Constructor> getAllStandings() {
        return constructorRepository.getStandings();
    }

    @Override
    public void removeAllStandings() {
        constructorRepository.removeAllStandings();
    }
}
