package com.formula1.standings.controller;

import com.formula1.standings.service.DriversStandingsService;
import com.formula1.standings.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
@RestController
@RequestMapping("/drivers")
public class DriversStandingsController {

    @Autowired
    private DriversStandingsService driversStandingsService;

    /**
     * Method returns the current list of F1 drivers standings
     * */
    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getStandings() {
        try {
            String result = driversStandingsService.getStandings(RedisConstants.DRIVERS);
            return ResponseEntity.ok()
                    .body(result);
        } catch (Exception e) {
            return new ResponseEntity("Unexpected error occured while getting standing data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method updates standing of a particular driver whose name is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON *MUST* include next properties: nationality, teamTitle.
     * Additional data for wins and points may be included.
     * */
    @RequestMapping(method = RequestMethod.POST, value = "/driver/{driverName}")
    public ResponseEntity updateDriverStanding(@PathVariable("driverName") String driverName, @RequestBody String driverData) {
        try {
            driversStandingsService.updateStandingsWithData(driverName, driverData);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Update of standing wasn't possible: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method updates standing of a particular driver whose name is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON *MUST* include next properties: nationality, teamTitle.
     * Additional data for wins and points may be included.
     * */
    @RequestMapping(method = RequestMethod.POST, value = "/driver")
    public ResponseEntity updateDriverStanding(@RequestBody String driverData) {
        try {
            driversStandingsService.updateStandingsWithData(driverData);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Update of standing wasn't possible: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
