package com.webService.standings.controller;

import com.businessModel.model.Driver;
import com.businessModel.service.DriversStandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

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
     */
    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getStandings() {
        Collection<Driver> result = driversStandingsService.getAllStandings();
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * Method updates standing of a particular driver whose name is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON *MUST* include next properties: nationality, teamTitle.
     * Additional data for wins and points may be included.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/driver/{driverName}")
    public ResponseEntity updateDriverStanding(@PathVariable("driverName") String driverName, @RequestBody String driverData) throws Exception {
        driversStandingsService.updateStandingsWithData(driverName, driverData);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Method updates standing of a particular driver whose name is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON *MUST* include next properties: nationality, teamTitle.
     * Additional data for wins and points may be included.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/driver")
    public ResponseEntity updateDriverStanding(@RequestBody String driverData) throws IOException {
        driversStandingsService.updateStandingsWithData(driverData);
        return new ResponseEntity(HttpStatus.OK);
    }
}
