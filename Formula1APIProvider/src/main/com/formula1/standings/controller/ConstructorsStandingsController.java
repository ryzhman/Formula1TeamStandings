package com.formula1.standings.controller;

import com.formula1.standings.service.ConstructorStandingService;
import com.formula1.standings.utils.RedisConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */

@RestController
@RequestMapping("/constructors")
public class ConstructorsStandingsController {

    @Autowired
    private ConstructorStandingService constructorStandingService;

    /**
     * Method returns the current list of F1 constructors standings
     */
    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getConstructorsStanding() {
        try {
            JSONObject result = constructorStandingService.getStandings(RedisConstants.CONSTRUCTORS);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unexpected error occured while getting standing data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method updates standing of a particular constructor which title is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON may include next property: points
     */
    @RequestMapping(method = RequestMethod.POST, value = "/constructor/{constructorTitle}")
    public ResponseEntity updateConstructorsStandings(@PathVariable("constructorTitle") String constructorTitle, @RequestBody String constructorsData) {
        try {
            constructorStandingService.updateStandingsWithData(constructorTitle, constructorsData);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Update of standing wasn't possible: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method updates standing of a particular constructor which title is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON may include next property: points
     */
    @RequestMapping(method = RequestMethod.POST, value = "/constructor")
    public ResponseEntity uploadConstructorsStandings(@RequestBody String constructorsData) {
        try {
            constructorStandingService.updateStandingsWithData(constructorsData);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Update of standing wasn't possible: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
