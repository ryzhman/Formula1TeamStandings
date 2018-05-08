package dataHandler.fetcher;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.businessModel.service.ConstructorsStandingService;
import com.businessModel.service.DriversStandingsService;
import dataHandler.utils.CountryUtils;
import dataHandler.utils.RequestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Service
@PropertySource("classpath:application.properties")
public class DataHandlerImpl implements DataHandler {
    private final Logger logger = LoggerFactory.getLogger(DataHandlerImpl.class);

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
    public void initDbWithDataFromServer() {
        fetchDataFromServer();
        handleData();
    }

    @Override
    public void fetchDataFromServer() {
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

    private List<Constructor> fetchConstructorData() {
        try {
            List<Constructor> result = new ArrayList<>();
            String dataFromServer = RequestUtils.getDataFromServer(constructorsStandingDataServer);
            Document doc = Jsoup.parse(dataFromServer, "UTF-8");
            Elements standingDiv = doc.getElementById("constructor-standing").getElementsByClass("gel-long-primer");
            //there are two elements in this collection
            if (CollectionUtils.isNotEmpty(standingDiv) && standingDiv.size() == 2) {
                Elements tableData = standingDiv.get(0).getElementsByTag("td");
                Iterator<Element> iterator = tableData.iterator();
                while (iterator.hasNext()) {
                    Constructor c = new Constructor(iterator.next().text());
                    if (!iterator.hasNext()) {
                        throw new RuntimeException("Data from the 3rd party server cannot be parsed");
                    }
                    c.setPoints(Integer.parseInt(iterator.next().text()));
                    result.add(c);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("Couldn't fetch data for constructors from server: " + constructorsStandingDataServer, e);
            return Collections.emptyList();
        }
    }

    private List<Driver> fetchDriverData() {
        try {
            String dataFromServer = RequestUtils.getDataFromServer(driversStandingDataServer);
            List<Driver> result = new ArrayList<>();
            Document doc = Jsoup.parse(dataFromServer, "UTF-8");
            Elements standingDiv = doc.getElementById("driver-standing").getElementsByClass("gel-long-primer");
            //there are two elements in this collection
            if (CollectionUtils.isNotEmpty(standingDiv) && standingDiv.size() == 2) {
                Elements tableData = standingDiv.get(0).getElementsByClass("table__row");
                Iterator<Element> iterator = tableData.iterator();
                while (iterator.hasNext()) {
                    Element currentElement = iterator.next();
                    Driver c = new Driver();

                    Elements headers = currentElement.getElementsByTag("th");
                    c.setPosition(Byte.parseByte(headers.get(0).text()));
                    String name = headers.get(1).getElementsByTag("abbr").attr("title");

                    Elements tData = currentElement.getElementsByTag("td");
                    String countryAcronym = getCountryAcronym(headers.get(1).getElementsByTag("img").first());
                    c.setName(name);
                    c.setNationality(countryAcronym);

                    Constructor constructor = new Constructor(tData.get(0).text().trim());
                    c.setTeam(constructor);

                    c.setWins(Integer.parseInt(tData.get(1).text()));
                    c.setPoints(Integer.parseInt(tData.get(2).text()));
                    result.add(c);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("Couldn't fetch data for drivers from server: " + driversStandingDataServer, e);
            return Collections.emptyList();
        }
    }

    private String getCountryAcronym(Element element) {
        String attr = element.getElementsByAttribute("src").attr("src");
        String iso2Acronym = attr.substring(attr.lastIndexOf("/") + 1, attr.lastIndexOf("."));
        return CountryUtils.getCountryByISO3Acronym(iso2Acronym);
    }

}
