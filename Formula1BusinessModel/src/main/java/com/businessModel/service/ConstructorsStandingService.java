package com.businessModel.service;

import com.businessModel.model.Constructor;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface ConstructorsStandingService {
    Constructor getByTitle(String title) throws IOException;

    void updateStandingsWithData(String constructorId, String constructorsData) throws IOException;

    void update(Constructor constructor);

    void updateStandingsWithData(List<Constructor> constructorList);

    Collection<Constructor> getAllStandings();

    void removeAllStandings();
}
