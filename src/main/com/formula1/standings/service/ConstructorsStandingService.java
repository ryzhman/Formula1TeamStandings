package com.formula1.standings.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.formula1.standings.entity.Constructor;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
public interface ConstructorsStandingService {
    String getStandings() throws JsonProcessingException;

    Constructor getConstructorByTitle(String title) throws IOException;

    void updateStandingsWithData(String constructorId, String constructorsData) throws IOException;
}
