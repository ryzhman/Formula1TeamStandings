package com.formula1.standings.service;

import com.formula1.standings.entity.Driver;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface DriversStandingsService {
    Driver getByName(String name) throws IOException;

    void updateStandingsWithData(String driverName, String driverData) throws Exception;

    void updateStandingsWithData(String driverData) throws IOException;

    void update(Driver driver) throws Exception;
}
