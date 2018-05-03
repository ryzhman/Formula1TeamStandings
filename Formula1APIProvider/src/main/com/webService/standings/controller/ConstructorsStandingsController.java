package com.webService.standings.controller;

import com.businessModel.model.Constructor;
import com.businessModel.service.ConstructorsStandingService;
import com.webService.standings.converter.ConstructorConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */

@RestController
@RequestMapping("/constructors")
public class ConstructorsStandingsController {
    @Autowired
    private ConstructorsStandingService constructorStandingService;

    /**
     * Method returns the current list of F1 constructors standings
     */
    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getConstructorsStanding() {
        List<Constructor> result = constructorStandingService.getAllStandings();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Method updates standing of a particular constructor which title is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON may include next property: points
     */
    @RequestMapping(method = RequestMethod.POST, value = "/constructor/{constructorTitle}")
    public ResponseEntity updateConstructorsStandings(@PathVariable("constructorTitle") String constructorTitle, @RequestBody String constructorsData) throws IOException {
        constructorStandingService.updateStandingsWithData(constructorTitle, constructorsData);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Method updates standing of a particular constructor which title is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON may include next property: points
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity uploadConstructorsStandings(@RequestBody String constructorsData) throws IOException {
        List<Constructor> constructors = ConstructorConverter.convertToEntities(constructorsData);
        constructorStandingService.updateStandingsWithData(constructors);
        return new ResponseEntity(HttpStatus.OK);
    }
}
