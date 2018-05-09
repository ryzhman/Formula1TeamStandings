package com.businessModel.converter;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public class RawDataConverter {
    protected static ObjectMapper mapper = new ObjectMapper();

    public static Collection<Driver> convertToDrivers(List<Object> rawData) {
        final Supplier<TreeSet<Driver>> supplier =
                () -> new TreeSet<>(Comparator.comparingInt(Driver::getPosition));
        return rawData.stream()
                .map(rawObject -> {
                    try {
                        return mapper.readValue((String) rawObject, Driver.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot fetch data from the DB");
                    }
                })
                .collect(Collectors.toCollection(supplier));
    }

    public static Collection<Constructor> convertToConstructors(List<Object> rawData) {
        final Supplier<TreeSet<Constructor>> supplier =
                () -> new TreeSet<>(Comparator.comparingInt(Constructor::getPosition));
        return rawData.stream()
                .map(rawObject -> {
                    try {
                        return mapper.readValue((String) rawObject, Constructor.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot fetch data from the DB");
                    }
                })
                .collect(Collectors.toCollection(supplier));
    }


}
