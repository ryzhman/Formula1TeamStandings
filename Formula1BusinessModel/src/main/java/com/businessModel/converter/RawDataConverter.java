package com.businessModel.converter;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public class RawDataConverter {

    public static List<Driver> convertToDrivers(List<Object> rawData) {
        return rawData.stream()
                .map(rawObject -> {
//                    (String)rawObject;
                    Driver driver = new Driver();

                    return driver;
                })
                .collect(Collectors.toList());
    }

    public static List<Constructor> convertToConstructors(List<Object> rawData) {
        return rawData.stream()
                .map(rawObject -> {
//                    (String)rawObject;
                    Constructor constructor = new Constructor();

                    return constructor;
                })
                .collect(Collectors.toList());
    }


}
