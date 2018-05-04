package com.businessModel.converter;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public class RawDataConverter {
    protected static ObjectMapper mapper = new ObjectMapper();

    public static List<Driver> convertToDrivers(List<Object> rawData) {
        return rawData.stream()
                .map(rawObject -> {
                    try {
                        Driver result = mapper.readValue((String)rawObject, Driver.class);
                        return result;
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot fetch data from the DB");
                    }
                })
                .collect(Collectors.toList());
    }

    public static List<Constructor> convertToConstructors(List<Object> rawData) {
        return rawData.stream()
                .map(rawObject -> {
                    try {
                        Constructor result = mapper.readValue((String)rawObject, Constructor.class);
                        return result;
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot fetch data from the DB");
                    }
                })
                .collect(Collectors.toList());
    }


}
