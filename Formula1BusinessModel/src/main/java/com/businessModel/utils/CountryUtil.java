package com.businessModel.utils;

import java.util.*;

/**
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
public class CountryUtil {
    private static Set<String> countriesList = new HashSet<>();

    static {
        String[] isoCountries = Locale.getISOCountries();
        for(String countryCode: isoCountries){
            Locale obj = new Locale("", countryCode);
            countriesList.add(obj.getDisplayCountry());
        }
    }

    public static String getCountry(String nationality){
        if(countriesList.contains(nationality)){
            return nationality;
        } else {
            return "n/a";
        }
    }
}
