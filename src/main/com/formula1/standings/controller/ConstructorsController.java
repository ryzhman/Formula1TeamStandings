package com.formula1.standings.controller;

import com.formula1.standings.service.ConstructorsStandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */

@RestController
@RequestMapping("/constuctors")
public class ConstructorsController {

    @Autowired
    private ConstructorsStandingService constructorsStandingServiceImpl;

    @RequestMapping(method = RequestMethod.GET, value = "/standings")
    public ResponseEntity getConstructorsStanding() {
        try {
            String result = constructorsStandingServiceImpl.getStandings();
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unexpected error occured while getting standing data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/constructor/{constructorTitle}")
    public ResponseEntity updateConstructorsStandings(@PathVariable("constructorTitle") String constructorTitle, @RequestBody String constructorsData) {
        try {
            constructorsStandingServiceImpl.updateStandingsWithData(constructorTitle, constructorsData);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity("Update of standing wasn't possible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
