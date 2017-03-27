package com.formula1.standings.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formula1.standings.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
public abstract class AbstractStandingsService {
    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected StringRedisTemplate redisTemplate;

    public String getStandings(String dataType) throws JsonProcessingException {
        String type = dataType.toUpperCase();
        if(type != RedisConstants.DRIVERS && type != RedisConstants.CONSTRUCTORS){
            throw new IllegalArgumentException("Incorrect key for DB was passed");
        }

        Set<String> allKeys  = redisTemplate.keys(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + type + RedisConstants.REDIS_SEPARATOR + "*");
        List<Object> fetchedData = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            Iterator iter = allKeys.iterator();
            while (iter.hasNext()) {
                connection.get(((String) iter.next()).getBytes());
            }
            return null;
        });
        return mapper.writeValueAsString(fetchedData);
    }
}
