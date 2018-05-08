package com.webService.standings.controller;

import com.businessModel.model.Constructor;
import com.businessModel.service.ConstructorsStandingService;
import com.webService.standings.converter.ConstructorConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */

@RestController
@RequestMapping("/constructors")
public class ConstructorsStandingsController {
    private final Logger logger = LoggerFactory.getLogger(ConstructorsStandingsController.class);

    @Autowired
    private ConstructorsStandingService constructorStandingService;

    /**
     * Method returns the current list of F1 constructors standings
     */
    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getConstructorsStanding(HttpServletRequest request) {
        List<Constructor> result = constructorStandingService.getAllStandings();
        logger.info("Constructor standing request was made, " + request.getRemoteUser() + ", " + request.getRemoteAddr());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Method updates standing of a particular constructor which title is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON may include next property: points
     */
    @RequestMapping(method = RequestMethod.POST, value = "/constructor/{constructorTitle}")
    public ResponseEntity updateConstructorsStandings(@PathVariable("constructorTitle") String constructorTitle, @RequestBody String constructorsData, HttpServletRequest request) throws IOException {
        constructorStandingService.updateStandingsWithData(constructorTitle, constructorsData);
        logger.info("Constructor standing for " + constructorTitle + " was updated by POST, " + request.getRemoteUser() + ", " + request.getRemoteAddr());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Method updates standing of a particular constructor which title is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON may include next property: points
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity uploadConstructorsStandings(@RequestBody String constructorsData, HttpServletRequest request) {
        try {
            List<Constructor> constructors = ConstructorConverter.convertToEntities(constructorsData);
            constructorStandingService.updateStandingsWithData(constructors);
            logger.info("Constructors standing was updated by POST, " + request.getRemoteUser() + ", " + request.getRemoteAddr());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Couldn't update constructors standings with data: " + constructorsData, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
