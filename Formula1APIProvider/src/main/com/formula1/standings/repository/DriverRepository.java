package com.formula1.standings.repository;

import com.formula1.standings.entity.Driver;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface DriverRepository {
    Driver getDriverByName(String name) throws IOException;

    void saveOrUpdate(Driver entity) throws Exception;
}
