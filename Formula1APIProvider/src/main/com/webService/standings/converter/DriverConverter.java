package com.webService.standings.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.businessModel.model.Driver;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public class DriverConverter {
    protected static ObjectMapper mapper = new ObjectMapper();

    public static Driver convert(String fetchedData) throws IOException {
        return mapper.readValue(fetchedData, Driver.class);
    }

    public static String convert(Driver entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }
}
