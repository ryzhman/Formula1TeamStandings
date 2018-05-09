package com.businessModel.repository;

import com.businessModel.converter.RawDataConverter;
import com.businessModel.model.Driver;
import com.businessModel.utils.RedisConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    @Override
    public void removeAllStandings() {
        try {
            redisTemplate.delete(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + "*");
        } catch (Exception e) {
            logger.error("Exception during clearing drivers standings", e);
        }
    }

    @Override
    public Collection<Driver> getStandings() {
        Set<String> allKeys = redisTemplate.keys(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + "*");
        List<Object> fetchedData = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            Iterator iter = allKeys.iterator();
            while (iter.hasNext()) {
                connection.get(((String) iter.next()).getBytes());
            }
            return null;
        });
        return RawDataConverter.convertToDrivers(fetchedData);
    }
}
