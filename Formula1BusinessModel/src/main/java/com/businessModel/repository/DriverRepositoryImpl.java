package com.businessModel.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.businessModel.model.Driver;
import com.businessModel.utils.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Repository
public class DriverRepositoryImpl implements DriverRepository {
    private final Logger logger = LoggerFactory.getLogger(DriverRepositoryImpl.class);
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    protected StringRedisTemplate redisTemplate;

    @Override
    public Driver getDriverByName(String name) {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + name);
        if (fetchedData == null) {
            return null;
        }
        try {
            return mapper.readValue(fetchedData, Driver.class);
        } catch (IOException e) {
            logger.error("Exception during fetching driver by name: " + name, e);
            return null;
        }
    }

    @Override
    public void saveOrUpdate(Driver entity) {
        try {
            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + entity.getName(),
                    mapper.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
            logger.error("Exception during saving or updating driver: " + entity.toString(), e);
        }
    }
}
