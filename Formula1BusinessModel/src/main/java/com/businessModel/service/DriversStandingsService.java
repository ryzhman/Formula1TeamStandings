package com.businessModel.service;

import com.businessModel.model.Driver;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface DriversStandingsService {
    Driver getByName(String name) throws IOException;

    void updateStandingsWithData(String driverName, String driverData) throws Exception;

    void updateStandingsWithData(String driverData) throws IOException;

    void update(Driver driver) throws Exception;

    Collection<Driver> getAllStandings();
}
