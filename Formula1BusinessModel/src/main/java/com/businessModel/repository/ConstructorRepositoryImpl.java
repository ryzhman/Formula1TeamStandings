package com.businessModel.repository;

import com.businessModel.model.Constructor;
import com.businessModel.utils.RedisConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ConstructorRepositoryImpl implements ConstructorRepository {
    private final Logger logger = LoggerFactory.getLogger(ConstructorRepositoryImpl.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveOrUpdate(Constructor constructor) {
        try {
            Constructor entity = getConstructorByTitle(constructor.getTitle());
            if (entity != null) {
                entity.setPoints(constructor.getPoints());
                entity.setPosition(constructor.getPosition());
                redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + entity.getTitle(),
                        mapper.writeValueAsString(entity));
            } else {
                redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + entity.getTitle(),
                        mapper.writeValueAsString(constructor));
            }
        } catch (IOException e) {
            logger.error("Exception during saving or updating the constructor: " + constructor.toString(), e);
        }
    }

    @Override
    public Constructor getConstructorByTitle(String title) {
        try {
            String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + title);
            if (fetchedData == null) {
                return null;
            }
            return mapper.readValue(fetchedData, Constructor.class);
        } catch (IOException e) {
            logger.error("Exception during fetching a constructor with title " + title, e);
            return null;
        }
    }


}
