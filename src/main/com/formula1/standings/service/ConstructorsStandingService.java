package com.formula1.standings.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.formula1.standings.entity.Constructor;
import com.formula1.standings.utils.DataValidator;
import com.formula1.standings.utils.RedisConstants;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
@Service
public class ConstructorsStandingService extends AbstractStandingsService {

    /**
     * Method return an entity found in Redis
     */
    public Constructor getConstructorByTitle(String title) throws IOException {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + title);
        if (fetchedData == null) {
            return null;
        }
        Constructor entity = mapper.readValue(fetchedData, Constructor.class);
        return entity;
    }

    /**
     * Method updates Redis data for particular constructor with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether constructor already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    public void updateStandingsWithData(String constructorId, String constructorsData) throws IOException {
        JsonNode json = mapper.readTree(constructorsData);

        DataValidator.validateConstructorData(json);

        Constructor entity = getConstructorByTitle(constructorId);
        if (entity != null) {
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + entity.getTitle(),
                    mapper.writeValueAsString(entity));
        } else {
            entity = new Constructor(constructorId);
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + entity.getTitle(),
                    mapper.writeValueAsString(entity));
        }
    }

    /**
     * Method updates Redis data for a bench of constructors with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether constructor already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    public void updateStandingsWithData(String constructorsData) throws IOException {
        JSONArray json = new JSONArray(constructorsData);

        DataValidator.validateConstructorsData(json);

        List<Constructor> construtorList = mapper.readValue(constructorsData, mapper.getTypeFactory().constructCollectionType(List.class, Constructor.class));
        construtorList.stream()
                .forEach(constructor -> {
                    try {
                        Constructor entity = getConstructorByTitle(constructor.getTitle());
                        if (entity != null) {
                            entity.setPoints(constructor.getPoints());
                            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + entity.getTitle(),
                                    mapper.writeValueAsString(entity));
                        } else {
                            entity = new Constructor(constructor.getTitle());
                            entity.setPoints(constructor.getPoints());
                            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + entity.getTitle(),
                                    mapper.writeValueAsString(entity));
                        }
                    } catch (IOException e) {
                        System.out.println("Exception occured: " + e.getMessage());
                    }
                });
    }
}
