package com.businessModel.repository;

import com.businessModel.model.Driver;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface DriverRepository {
    Driver getDriverByName(String name) throws IOException;

    void saveOrUpdate(Driver entity) throws Exception;
}
