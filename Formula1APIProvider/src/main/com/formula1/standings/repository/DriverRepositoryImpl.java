package com.formula1.standings.repository;

import com.formula1.standings.converter.DriverConverter;
import com.formula1.standings.entity.Driver;
import com.formula1.standings.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Repository
public class DriverRepositoryImpl implements DriverRepository {
    @Autowired
    protected StringRedisTemplate redisTemplate;

    @Override
    public Driver getDriverByName(String name) throws IOException {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + name);
        if (fetchedData == null) {
            return null;
        }
        return DriverConverter.convertToDriver(fetchedData);
    }

    @Override
    public void saveOrUpdate(Driver entity) throws Exception {
        redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + entity.getName(),
                DriverConverter.convertToString(entity));
    }
}
