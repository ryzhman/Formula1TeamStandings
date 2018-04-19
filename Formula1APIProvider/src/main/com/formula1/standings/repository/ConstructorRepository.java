package com.formula1.standings.repository;

import com.formula1.standings.entity.Constructor;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface ConstructorRepository {
    void saveOrUpdate(Constructor constructor);

    Constructor getConstructorByTitle(String title) throws IOException;
}
