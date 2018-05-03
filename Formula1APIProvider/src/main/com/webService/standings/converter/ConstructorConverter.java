package com.webService.standings.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.businessModel.model.Constructor;
import com.businessModel.utils.DataValidator;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public class ConstructorConverter {
    protected static ObjectMapper mapper = new ObjectMapper();

    public static List<Constructor> convertToEntities(String constructorsData) throws IOException {
        JSONArray json = new JSONArray(constructorsData);

        DataValidator.validateConstructorsData(json);
        return mapper.readValue(constructorsData, mapper.getTypeFactory().constructCollectionType(List.class, Constructor.class));
    }

    public static Constructor convertToEntity(String fetchedData) throws IOException {
        return mapper.readValue(fetchedData, Constructor.class);
    }

    public static String convertToString(Constructor entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }

}
