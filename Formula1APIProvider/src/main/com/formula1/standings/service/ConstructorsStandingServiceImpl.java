package com.formula1.standings.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formula1.standings.converter.ConstructorConverter;
import com.formula1.standings.entity.Constructor;
import com.formula1.standings.repository.ConstructorRepository;
import com.formula1.standings.utils.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
@Service
public class ConstructorsStandingServiceImpl extends AbstractStandingsService implements ConstructorsStandingService {
    @Autowired
    private ConstructorRepository constructorRepository;

    /**
     * Method return an entity found in Redis
     */
    @Override
    public Constructor getByTitle(String title) throws IOException {
        return constructorRepository.getConstructorByTitle(title);
    }

    /**
     * Method updates Redis data for particular constructor with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether constructor already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    @Override
    public void updateStandingsWithData(String constructorId, String constructorsData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(constructorsData);

        DataValidator.validateConstructorData(json);

        Constructor entity = getByTitle(constructorId);
        if (entity != null) {
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            constructorRepository.saveOrUpdate(entity);
        } else {
            entity = new Constructor(constructorId);
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            constructorRepository.saveOrUpdate(entity);
        }
    }

    @Override
    public void update(Constructor constructor) {
        constructorRepository.saveOrUpdate(constructor);
    }

    /**
     * Method updates Redis data for a bench of constructors with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether constructor already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    @Override
    public void updateStandingsWithData(String constructorsData) throws IOException {
        List<Constructor> constructorList = ConstructorConverter.convertToEntities(constructorsData);
        constructorList.forEach(constructor -> constructorRepository.saveOrUpdate(constructor));
    }
}
