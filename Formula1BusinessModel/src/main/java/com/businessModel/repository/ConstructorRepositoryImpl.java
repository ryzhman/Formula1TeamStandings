package com.businessModel.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.businessModel.model.Constructor;
import com.businessModel.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Repository
public class ConstructorRepositoryImpl implements ConstructorRepository {
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveOrUpdate(Constructor constructor) {
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
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    @Override
    public Constructor getConstructorByTitle(String title) throws IOException {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.CONSTRUCTORS + RedisConstants.REDIS_SEPARATOR + title);
        if (fetchedData == null) {
            return null;
        }
        return mapper.readValue(fetchedData, Constructor.class);
    }


}
