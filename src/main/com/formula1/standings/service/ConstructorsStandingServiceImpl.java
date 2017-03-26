package com.formula1.standings.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formula1.standings.entity.Constructor;
import com.formula1.standings.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
@Service
public class ConstructorsStandingServiceImpl implements ConstructorsStandingService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String getStandings() throws JsonProcessingException {
        StringBuilder result = new StringBuilder();
        Set<String> allKeys = redisTemplate.keys(RedisConstants.STANDINGS + RedisConstants.REDIS_DELIMETER + RedisConstants.CONSTRUCTS + RedisConstants.REDIS_DELIMETER + "*");
        List<Object> fetchedData = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            Iterator iter = allKeys.iterator();
            while (iter.hasNext()) {
                connection.get(((String) iter.next()).getBytes());
            }
            return null;
        });

        return mapper.writeValueAsString(fetchedData);
    }

    @Override
    public Constructor getConstructorByTitle(String title) throws IOException {
        String fetchedData = redisTemplate.opsForValue().get(RedisConstants.STANDINGS + RedisConstants.REDIS_DELIMETER + RedisConstants.CONSTRUCTS + RedisConstants.REDIS_DELIMETER + title);
        if (fetchedData == null) {
            return null;
        }
        Constructor entity = mapper.readValue(fetchedData, Constructor.class);
        return entity;
    }

    @Override
    public void updateStandingsWithData(String constructorId, String constructorsData) throws IOException {
        Constructor entity = getConstructorByTitle(constructorId);
        if (entity != null) {
            String node = mapper.writeValueAsString(entity);
            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_DELIMETER + RedisConstants.CONSTRUCTS + RedisConstants.REDIS_DELIMETER + entity.getTitle(),
                    node);
        } else {
            entity = new Constructor(constructorId);
            JsonNode node = mapper.readTree(constructorsData);
            redisTemplate.opsForValue().set(RedisConstants.STANDINGS + RedisConstants.REDIS_DELIMETER + RedisConstants.CONSTRUCTS + RedisConstants.REDIS_DELIMETER + entity.getTitle(),
                    node.toString());
        }
    }
}
