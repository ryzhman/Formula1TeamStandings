package dataHandler.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Oleksandr Ryzhkov on 03.05.2018.
 */
public class CountryUtils {
    private static Map<String, Locale> iocToLocaleMap = new HashMap<>();
    private static Map<String, Locale> iso3ToLocaleMap = new HashMap<>();

    static {
        String[] countries = Locale.getISOCountries();
        for (String country : countries) {
            Locale locale = new Locale("", country);
            iso3ToLocaleMap.put(locale.getISO3Country().toUpperCase(), locale);
        }

        //popupaling map with ICO (International Olympic Comittee)to Locale
        iocToLocaleMap.put("ALG", iso3ToLocaleMap.get("DZA"));
        iocToLocaleMap.put("ASA", iso3ToLocaleMap.get("ASM"));
        iocToLocaleMap.put("ANG", iso3ToLocaleMap.get("AGO"));
        iocToLocaleMap.put("ANT", iso3ToLocaleMap.get("ATG"));
        iocToLocaleMap.put("ARU", iso3ToLocaleMap.get("ABW"));
        iocToLocaleMap.put("BAH", iso3ToLocaleMap.get("BHS"));
        iocToLocaleMap.put("BRN", iso3ToLocaleMap.get("BHR"));
        iocToLocaleMap.put("BAN", iso3ToLocaleMap.get("BGD"));
        iocToLocaleMap.put("BAR", iso3ToLocaleMap.get("BRB"));
        iocToLocaleMap.put("BIZ", iso3ToLocaleMap.get("BLZ"));
        iocToLocaleMap.put("BER", iso3ToLocaleMap.get("BMU"));
        iocToLocaleMap.put("BHU", iso3ToLocaleMap.get("BTN"));
        iocToLocaleMap.put("BOT", iso3ToLocaleMap.get("BWA"));
        iocToLocaleMap.put("IVB", iso3ToLocaleMap.get("VGB"));
        iocToLocaleMap.put("BRU", iso3ToLocaleMap.get("BRN"));
        iocToLocaleMap.put("BUL", iso3ToLocaleMap.get("BGR"));
        iocToLocaleMap.put("BUR", iso3ToLocaleMap.get("BFA"));
        iocToLocaleMap.put("CAM", iso3ToLocaleMap.get("KHM"));
        iocToLocaleMap.put("CAY", iso3ToLocaleMap.get("CYM"));
        iocToLocaleMap.put("CHA", iso3ToLocaleMap.get("TCD"));
        iocToLocaleMap.put("CHI", iso3ToLocaleMap.get("CHL"));
        iocToLocaleMap.put("CGO", iso3ToLocaleMap.get("COG"));
        iocToLocaleMap.put("CRC", iso3ToLocaleMap.get("CRI"));
        iocToLocaleMap.put("CRO", iso3ToLocaleMap.get("HRV"));
        iocToLocaleMap.put("DEN", iso3ToLocaleMap.get("DNK"));
        iocToLocaleMap.put("ESA", iso3ToLocaleMap.get("SLV"));
        iocToLocaleMap.put("GEQ", iso3ToLocaleMap.get("GNQ"));
        iocToLocaleMap.put("FIJ", iso3ToLocaleMap.get("FJI"));
        iocToLocaleMap.put("GAM", iso3ToLocaleMap.get("GMB"));
        iocToLocaleMap.put("GER", iso3ToLocaleMap.get("DEU"));
        iocToLocaleMap.put("GRE", iso3ToLocaleMap.get("GRC"));
        iocToLocaleMap.put("GRN", iso3ToLocaleMap.get("GRD"));
        iocToLocaleMap.put("GUA", iso3ToLocaleMap.get("GTM"));
        iocToLocaleMap.put("GUI", iso3ToLocaleMap.get("GIN"));
        iocToLocaleMap.put("GBS", iso3ToLocaleMap.get("GNB"));
        iocToLocaleMap.put("HAI", iso3ToLocaleMap.get("HTI"));
        iocToLocaleMap.put("HON", iso3ToLocaleMap.get("HND"));
        iocToLocaleMap.put("INA", iso3ToLocaleMap.get("IDN"));
        iocToLocaleMap.put("IRI", iso3ToLocaleMap.get("IRN"));
        iocToLocaleMap.put("KUW", iso3ToLocaleMap.get("KWT"));
        iocToLocaleMap.put("LAT", iso3ToLocaleMap.get("LVA"));
        iocToLocaleMap.put("LIB", iso3ToLocaleMap.get("LBN"));
        iocToLocaleMap.put("LES", iso3ToLocaleMap.get("LSO"));
        iocToLocaleMap.put("LBA", iso3ToLocaleMap.get("LBY"));
        iocToLocaleMap.put("MAD", iso3ToLocaleMap.get("MDG"));
        iocToLocaleMap.put("MAW", iso3ToLocaleMap.get("MWI"));
        iocToLocaleMap.put("MAS", iso3ToLocaleMap.get("MYS"));
        iocToLocaleMap.put("MTN", iso3ToLocaleMap.get("MRT"));
        iocToLocaleMap.put("MRI", iso3ToLocaleMap.get("MUS"));
        iocToLocaleMap.put("MON", iso3ToLocaleMap.get("MCO"));
        iocToLocaleMap.put("MGL", iso3ToLocaleMap.get("MNG"));
        iocToLocaleMap.put("MYA", iso3ToLocaleMap.get("MMR"));
        iocToLocaleMap.put("NEP", iso3ToLocaleMap.get("NPL"));
        iocToLocaleMap.put("NED", iso3ToLocaleMap.get("NLD"));
        iocToLocaleMap.put("NCA", iso3ToLocaleMap.get("NIC"));
        iocToLocaleMap.put("NIG", iso3ToLocaleMap.get("NER"));
        iocToLocaleMap.put("NGR", iso3ToLocaleMap.get("NGA"));
        iocToLocaleMap.put("OMA", iso3ToLocaleMap.get("OMN"));
        iocToLocaleMap.put("PLE", iso3ToLocaleMap.get("PSE"));
        iocToLocaleMap.put("PAR", iso3ToLocaleMap.get("PRY"));
        iocToLocaleMap.put("PHI", iso3ToLocaleMap.get("PHL"));
        iocToLocaleMap.put("POR", iso3ToLocaleMap.get("PRT"));
        iocToLocaleMap.put("PUR", iso3ToLocaleMap.get("PRI"));
        iocToLocaleMap.put("SKN", iso3ToLocaleMap.get("KNA"));
        iocToLocaleMap.put("VIN", iso3ToLocaleMap.get("VCT"));
        iocToLocaleMap.put("SAM", iso3ToLocaleMap.get("WSM"));
        iocToLocaleMap.put("KSA", iso3ToLocaleMap.get("SAU"));
        iocToLocaleMap.put("SEY", iso3ToLocaleMap.get("SYC"));
        iocToLocaleMap.put("SIN", iso3ToLocaleMap.get("SGP"));
        iocToLocaleMap.put("SLO", iso3ToLocaleMap.get("SVN"));
        iocToLocaleMap.put("SOL", iso3ToLocaleMap.get("SLB"));
        iocToLocaleMap.put("RSA", iso3ToLocaleMap.get("ZAF"));
        iocToLocaleMap.put("SRI", iso3ToLocaleMap.get("LKA"));
        iocToLocaleMap.put("SUD", iso3ToLocaleMap.get("SDN"));
        iocToLocaleMap.put("SUI", iso3ToLocaleMap.get("CHE"));
        iocToLocaleMap.put("TPE", iso3ToLocaleMap.get("TWN"));
        iocToLocaleMap.put("TAN", iso3ToLocaleMap.get("TZA"));
        iocToLocaleMap.put("TOG", iso3ToLocaleMap.get("TGO"));
        iocToLocaleMap.put("TGA", iso3ToLocaleMap.get("TON"));
        iocToLocaleMap.put("TRI", iso3ToLocaleMap.get("TTO"));
        iocToLocaleMap.put("UAE", iso3ToLocaleMap.get("ARE"));
        iocToLocaleMap.put("ISV", iso3ToLocaleMap.get("VIR"));
        iocToLocaleMap.put("URU", iso3ToLocaleMap.get("URY"));
        iocToLocaleMap.put("VAN", iso3ToLocaleMap.get("VUT"));
        iocToLocaleMap.put("VIE", iso3ToLocaleMap.get("VNM"));
        iocToLocaleMap.put("ZAM", iso3ToLocaleMap.get("ZMB"));
        iocToLocaleMap.put("ZIM", iso3ToLocaleMap.get("ZWE"));
    }

    public static String getCountryByISO3Acronym(String iso3Acronym) {
        Locale result = iocToLocaleMap.get(iso3Acronym.toUpperCase());
        if (result == null) {
            result = iso3ToLocaleMap.get(iso3Acronym.toUpperCase());
        }
        if (result != null) {
            return result.getCountry();
        } else
            return null;
    }
}
