package com.businessModel.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.businessModel.model.Driver;
import com.businessModel.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Repository
public class DriverRepositoryImpl implements DriverRepository {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    protected StringRedisTemplate redisTemplate;

    @Override
    public Driver getDriverByName(String name) throws IOException {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + name);
        if (fetchedData == null) {
            return null;
        }
        return mapper.readValue(fetchedData, Driver.class);
    }

    @Override
    public void saveOrUpdate(Driver entity) throws Exception {
        redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + entity.getName(),
                mapper.writeValueAsString(entity));
    }
}
