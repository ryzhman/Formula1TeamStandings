package com.businessModel.repository;

import com.businessModel.model.Constructor;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface ConstructorRepository {
    void saveOrUpdate(Constructor constructor);

    Constructor getConstructorByTitle(String title) throws IOException;
}
