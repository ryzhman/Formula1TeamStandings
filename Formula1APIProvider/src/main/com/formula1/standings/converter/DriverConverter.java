package com.formula1.standings.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formula1.standings.entity.Driver;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public class DriverConverter {
    protected static ObjectMapper mapper = new ObjectMapper();

    public static Driver convertToDriver(String fetchedData) throws IOException {
        return mapper.readValue(fetchedData, Driver.class);
    }

    public static String convertToString(Driver entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }
}
