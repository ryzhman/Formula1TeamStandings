package dataHandler.fetcher;

import com.businessModel.model.Constructor;
import com.businessModel.model.Contestant;
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
import java.util.*;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
@Service
@PropertySource("classpath:application.properties")
public class DataHandlerImpl implements DataHandler {
    private final Logger logger = LoggerFactory.getLogger(DataHandlerImpl.class);

    private Collection<Driver> driversData;
    private Collection<Constructor> constructorsData;

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

    private Collection<Constructor> fetchConstructorData() {
        try {
            Set<Constructor> result = new TreeSet<>(Comparator.comparingInt(Contestant::getPosition));
            String dataFromServer = RequestUtils.getDataFromServer(constructorsStandingDataServer);
            Document doc = Jsoup.parse(dataFromServer, "UTF-8");
            Elements standingDiv = doc.getElementById("constructor-standing").getElementsByClass("gel-long-primer");
            //there are two elements in this collection
            if (CollectionUtils.isNotEmpty(standingDiv) && standingDiv.size() == 2) {
                Elements tableData = standingDiv.get(0).getElementsByTag("tr");
                tableData.removeIf(item -> " ".equals(item.text()));
                Iterator<Element> iterator = tableData.iterator();
                while (iterator.hasNext()) {
                    Element current = iterator.next();
                    Constructor c = new Constructor(current.getElementsByTag("td").first().text());
                    c.setPoints(Byte.parseByte(current.getElementsByTag("td").last().text()));
                    c.setPosition(Byte.parseByte(current.getElementsByTag("th").text()));
                    result.add(c);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("Couldn't fetch data for constructors from server: " + constructorsStandingDataServer, e);
            return Collections.emptySet();
        }
    }

    private Collection<Driver> fetchDriverData() {
        try {
            String dataFromServer = RequestUtils.getDataFromServer(driversStandingDataServer);
            Set<Driver> result = new TreeSet<>(Comparator.comparingInt(Driver::getPosition));
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
                    c.setPoints(Short.parseShort(tData.get(2).text()));
                    result.add(c);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("Couldn't fetch data for drivers from server: " + driversStandingDataServer, e);
            return Collections.emptySet();
        }
    }

    private String getCountryAcronym(Element element) {
        String attr = element.getElementsByAttribute("src").attr("src");
        String iso2Acronym = attr.substring(attr.lastIndexOf("/") + 1, attr.lastIndexOf("."));
        return CountryUtils.getCountryByISO3Acronym(iso2Acronym);
    }

}
