package com.businessModel.service;

import com.businessModel.converter.RawDataConverter;
import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.businessModel.repository.DriverRepository;
import com.businessModel.utils.DataValidator;
import com.businessModel.utils.RedisConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
@Service
public class DriversStandingsServiceImpl implements DriversStandingsService {
    private final Logger logger = LoggerFactory.getLogger(DriversStandingsServiceImpl.class);
    @Autowired
    protected StringRedisTemplate redisTemplate;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ConstructorsStandingService constructorsStandingService;

    /**
     * Method return an entity found in Redis
     */
    @Override
    public Driver getByName(String name) throws IOException {
        return driverRepository.getDriverByName(name);
    }

    /**
     * Method updates Redis data for particular driver with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether driver already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    @Override
    public void updateStandingsWithData(String driverName, String driverData) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode json = mapper.readTree(driverData);

            DataValidator.validateDriverData(json);
            Driver entity = getByName(driverName);
            if (entity != null) {
                entity.setNationality(json.get("nationality").toString());
                entity.setPoints(Short.parseShort(json.get("points").toString()));
                Constructor team = constructorsStandingService.getByTitle(json.get("teamTitle").toString());
                if (team == null) {
                    throw new RuntimeException("Cannot store data for driver with a team title: '" + json.get("teamTitle").toString() + "'");
                }
                entity.setTeam(team);
                entity.setWins(Integer.parseInt(json.get("wins").toString()));
                driverRepository.saveOrUpdate(entity);
            } else {
                Constructor team = constructorsStandingService.getByTitle(json.get("teamTitle").toString());
                if (team == null) {
                    throw new RuntimeException("Cannot store data for driver with a team title: '" + json.get("teamTitle").toString() + "'");
                }
                entity = new Driver(driverName, json.get("nationality").toString(), team);
                entity.setPoints(Short.parseShort(json.get("points").toString()));
                entity.setWins(Integer.parseInt(json.get("wins").toString()));
                driverRepository.saveOrUpdate(entity);
            }
        } catch (Exception e) {
            logger.error("Exception during updating driver: " + driverName + " with data:" + driverData, e);
        }
    }

    /**
     * Method updates Redis data for a bunch of drivers with information received from request body.
     * Validation of required data is made before persisting it.
     * Initially, whether driver already exists is conducted. If yes entity is updated, otherwise - persisted from scratch.
     */
    @Override
    public void updateStandingsWithData(String driverData) {
        try {
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
                            entity.setTeam(driver.getTeam());
                            entity.setWins(driver.getWins());

                            driverRepository.saveOrUpdate(entity);
                        } else {
                            entity = new Driver(driver.getName(), driver.getNationality(), driver.getTeam());
                            entity.setPoints(driver.getPoints());
                            entity.setWins(driver.getWins());
                            driverRepository.saveOrUpdate(entity);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception occured: " + e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            logger.error("Exception during updating drivers standings with data:" + driverData, e);
        }
    }

    @Override
    public void update(Driver driver) throws Exception {
        driverRepository.saveOrUpdate(driver);
    }

    @Override
    public Collection<Driver> getAllStandings() {
        Set<String> allKeys = redisTemplate.keys(RedisConstants.STANDINGS + RedisConstants.REDIS_SEPARATOR + RedisConstants.DRIVERS + RedisConstants.REDIS_SEPARATOR + "*");
        List<Object> fetchedData = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            Iterator iter = allKeys.iterator();
            while (iter.hasNext()) {
                connection.get(((String) iter.next()).getBytes());
            }
            return null;
        });
        return RawDataConverter.convertToDrivers(fetchedData);
    }
}
