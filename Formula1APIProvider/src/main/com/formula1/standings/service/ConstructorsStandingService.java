package com.formula1.standings.service;

import com.formula1.standings.entity.Constructor;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface ConstructorsStandingService {
    Constructor getByTitle(String title) throws IOException;

    void updateStandingsWithData(String constructorId, String constructorsData) throws IOException;

    void update(Constructor constructor);

    void updateStandingsWithData(String constructorsData) throws IOException;
}
