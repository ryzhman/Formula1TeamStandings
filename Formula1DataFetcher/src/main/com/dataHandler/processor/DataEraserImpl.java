package dataHandler.processor;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.businessModel.service.ConstructorsStandingService;
import com.businessModel.service.DriversStandingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Oleksandr Ryzhkov on 08.05.2018.
 */
@Component
public class DataEraserImpl implements DataEraser {
    private final Logger logger = LoggerFactory.getLogger(DataFetcherImpl.class);
    @Autowired
    private ConstructorsStandingService constructorStandingService;
    @Autowired
    private DriversStandingsService driversStandingsService;

    @Override
    public void eraseOldData() {
        constructorStandingService.removeAllStandings();
        driversStandingsService.removeAllStandings();
    }
}
