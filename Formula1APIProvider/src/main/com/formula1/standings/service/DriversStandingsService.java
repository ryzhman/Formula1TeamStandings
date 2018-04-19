package com.formula1.standings.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formula1.standings.entity.Driver;
import com.formula1.standings.repository.DriverRepository;
import com.formula1.standings.utils.DataValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
@Service
public class DriversStandingsService extends AbstractStandingsService {
    @Autowired
    private DriverRepository driverRepository;

    /**
     * Method return an entity found in Redis
     */
    public Driver getByName(String name) throws IOException {
        return driverRepository.getDriverByName(name);
    }

    /**
     * Method updates Redis data for particular driver with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether driver already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    public void updateStandingsWithData(String driverName, String driverData) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(driverData);

        DataValidator.validateDriverData(json);

        Driver entity = getByName(driverName);
        if (entity != null) {
            entity.setNationality(json.get("nationality").toString());
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            entity.setTeamTitle(json.get("teamTitle").toString());
            entity.setWins(Integer.parseInt(json.get("wins").toString()));
            driverRepository.saveOrUpdate(entity);
        } else {
            entity = new Driver(driverName, json.get("nationality").toString(), json.get("teamTitle").toString());
            entity.setPoints(Integer.parseInt(json.get("points").toString()));
            entity.setWins(Integer.parseInt(json.get("wins").toString()));
            driverRepository.saveOrUpdate(entity);
        }
    }

    /**
     * Method updates Redis data for a bench of drivers with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether driver already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    public void updateStandingsWithData(String driverData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray json = new JSONArray(driverData);

        DataValidator.validateDriversData(json);

        List<Driver> driversList = mapper.readValue(driverData, mapper.getTypeFactory().constructCollectionType(List.class, Driver.class));
        if (CollectionUtils.isNotEmpty(driversList)) {
            driversList.forEach(driver -> {
                try {
                    Driver entity = getByName(driver.getName());
                    if (entity != null) {
                        entity.setNationality(driver.getNationality());
                        entity.setPoints(driver.getPoints());
                        entity.setTeamTitle(driver.getTeamTitle());
                        entity.setWins(driver.getWins());

                        driverRepository.saveOrUpdate(entity);
                    } else {
                        entity = new Driver(driver.getName(), driver.getNationality(), driver.getTeamTitle());
                        entity.setPoints(driver.getPoints());
                        entity.setWins(driver.getWins());
                        driverRepository.saveOrUpdate(entity);
                    }
                } catch (Exception e) {
                    System.out.println("Exception occured: " + e.getMessage());
                }
            });
        }
    }

    public void update(Driver driver) throws Exception {
        driverRepository.saveOrUpdate(driver);
    }
}
