package dataHandler.job;

import dataHandler.processor.DataEraser;
import dataHandler.processor.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Oleksandr Ryzhkov on 08.05.2018.
 */
@Component
public class StandingUpdaterJob {
    private final Logger logger = LoggerFactory.getLogger(StandingUpdaterJob.class);

    @Autowired
    private DataFetcher dataFetcher;
    @Autowired
    private DataEraser dataEraser;

    //each Sunday at 20:00
    @Scheduled(cron = "0 0 20 * * Sun", zone ="GMT") //todo check when exactly results are published
    public void updateStandings() {
        try {
            logger.info("Data updating job started at: " + LocalDateTime.now());
            dataEraser.eraseOldData();
            logger.info("Removing old data finished at: " + LocalDateTime.now());
            dataFetcher.populateServiceWithStandings();
            logger.info("Database was populated with updated data at: " + LocalDateTime.now());
            logger.info("Data updating job finished at: " + LocalDateTime.now());
        } catch (Exception e) {
            logger.error("Exception occured while updating standings data with job", e);
        }
    }


}
