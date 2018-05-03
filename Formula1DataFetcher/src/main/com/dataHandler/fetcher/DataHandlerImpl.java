package dataHandler.fetcher;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.businessModel.service.ConstructorsStandingService;
import com.businessModel.service.DriversStandingsService;
import dataHandler.utils.RequestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Service
@PropertySource("classpath:application.properties")
public class DataHandlerImpl implements DataHandler {
    private List<Driver> driversData;
    private List<Constructor> constructorsData;

    @Value("${standing.drivers}")
    private String driversStandingDataServer;
    @Value("${standing.constructors}")
    private String constructorsStandingDataServer;

    @Autowired
    private ConstructorsStandingService constructorStandingService;
    @Autowired
    private DriversStandingsService driversStandingsService;

    @PostConstruct
    public void initDbWithDataFromServer() throws IOException {
        fetchDataFromServer();
        handleData();
    }

    @Override
    public void fetchDataFromServer() throws IOException {
        constructorsData = fetchConstructorData();
        driversData = fetchDriverData();
    }

    @Override
    public void handleData() {
        if (CollectionUtils.isNotEmpty(constructorsData)) {
            constructorsData.forEach(constructor -> constructorStandingService.update(constructor));
        }

        if (CollectionUtils.isNotEmpty(driversData)) {
            driversData.forEach(driver -> {
                try {
                    driversStandingsService.update(driver);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private List<Constructor> fetchConstructorData() throws IOException {
        String dataFromServer = RequestUtils.getDataFromServer(constructorsStandingDataServer);


        List<Constructor> result = new ArrayList<>();
        return result;
    }

    private List<Driver> fetchDriverData() throws IOException {
        String dataFromServer = RequestUtils.getDataFromServer(driversStandingDataServer);


        List<Driver> result = new ArrayList<>();
        return result;
    }

}
