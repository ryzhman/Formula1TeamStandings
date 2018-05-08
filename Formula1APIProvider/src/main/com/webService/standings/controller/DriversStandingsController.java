package com.webService.standings.controller;

import com.businessModel.model.Driver;
import com.businessModel.service.DriversStandingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
@RestController
@RequestMapping("/drivers")
public class DriversStandingsController {
    final static Logger logger = LoggerFactory.getLogger(DriversStandingsController.class);
    @Autowired
    private DriversStandingsService driversStandingsService;

    /**
     * Method returns the current list of F1 drivers standings
     */
    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getStandings(HttpServletRequest request) {
        Collection<Driver> result = driversStandingsService.getAllStandings();
        logger.info("Drivers standing request was made, " + request.getRemoteUser() + ", " + request.getRemoteAddr());
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
    public ResponseEntity updateDriverStanding(@PathVariable("driverName") String driverName, @RequestBody String driverData,
                                               HttpServletRequest request) throws Exception {
        driversStandingsService.updateStandingsWithData(driverName, driverData);
        logger.info("Driver standing for " + driverName + " was updated, " + request.getRemoteUser() + ", " + request.getRemoteAddr());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Method updates standing of a particular driver whose name is passed as a path variable.
     * Data to update must be included in request body.
     * The passed JSON *MUST* include next properties: nationality, teamTitle.
     * Additional data for wins and points may be included.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/drivers")
    public ResponseEntity updateDriverStanding(@RequestBody String driversData, HttpServletRequest request) throws IOException {
        driversStandingsService.updateStandingsWithData(driversData);
        logger.info("Drivers standings were updated, " + request.getRemoteUser() + ", " + request.getRemoteAddr());
        return new ResponseEntity(HttpStatus.OK);
    }
}
