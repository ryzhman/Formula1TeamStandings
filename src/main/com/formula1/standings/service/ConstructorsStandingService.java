package com.formula1.standings.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.formula1.standings.entity.Constructor;
import com.formula1.standings.utils.DataValidator;
import com.formula1.standings.utils.RedisConstants;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
@Service
public class ConstructorsStandingService extends AbstractStandingsService {

    /**
     * Method return an entity found in Redis
     * */
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
     * */
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
}
