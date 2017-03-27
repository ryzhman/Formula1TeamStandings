package com.formula1.standings.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.formula1.standings.entity.Driver;
import com.formula1.standings.utils.DataValidator;
import com.formula1.standings.utils.RedisConstants;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
@Service
public class DriversStandingsService extends AbstractStandingsService {

    /**
     * Method return an entity found in Redis
     * */
    public Driver getDriverByName(String name) throws IOException {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + name);
        if (fetchedData == null) {
            return null;
        }
        Driver entity = mapper.readValue(fetchedData, Driver.class);
        return entity;
    }

    /**
     * Method updates Redis data for particular driver with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether driver already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     * */
    public void updateStandingsWithData(String driverName, String driverData) throws IOException {
        JsonNode json = mapper.readTree(driverData);

        DataValidator.validateDriverData(json);

        Driver entity = getDriverByName(driverName);
        if (entity != null) {
            entity.setNationality(json.get("nationality").toString());
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            entity.setTeamTitle(json.get("teamTitle").toString());
            entity.setWins(Integer.parseInt(json.get("wins").toString()));

            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + entity.getName(),
                    mapper.writeValueAsString(entity));
        } else {
            entity = new Driver(driverName, json.get("nationality").toString(), json.get("teamTitle").toString());
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            entity.setWins(Integer.parseInt(json.get("wins").toString()));
            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + entity.getName(),
                    mapper.writeValueAsString(entity));
        }
    }
}
