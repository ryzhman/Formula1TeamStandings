package com.formula1.standings.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.json.JSONArray;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
public class DataValidator {

    public static void validateDriverData(JsonNode node) {
        //each value is checked for presence and implicitly stated null as value
        boolean isNationalityPresent = node.get("nationality") == null && node.get("nationality").equals(NullNode.getInstance());
        boolean isTeamTitlePresent = node.get("teamTitle") == null && node.get("nationality").equals(NullNode.getInstance());
        if (isNationalityPresent && isTeamTitlePresent) {
            throw new IllegalArgumentException("Required property wasn't passed or is null");
        }
        try {
            Integer.parseInt(node.get("points").toString());
            Integer.parseInt(node.get("wins").toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Required property is a character: " + e.getMessage());
        }
    }

    public static void validateDriversData(JSONArray node) {
        //each JSONObject is checked for presence and implicitly stated null as value
        for (int i = 0; i < node.length(); i++) {
            boolean isNationalityPresent = node.getJSONObject(i).get("nationality") == null && node.getJSONObject(i).get("nationality").equals(NullNode.getInstance());
            boolean isTeamTitlePresent = node.getJSONObject(i).get("teamTitle") == null && node.getJSONObject(i).get("nationality").equals(NullNode.getInstance());
            if (isNationalityPresent && isTeamTitlePresent) {
                throw new IllegalArgumentException("Required property wasn't passed or is null");
            }
            try {
                Integer.parseInt(node.getJSONObject(i).get("points").toString());
                Integer.parseInt(node.getJSONObject(i).get("wins").toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Required property is a character: " + e.getMessage());
            }
        }
    }

    public static void validateConstructorData(JsonNode node) {
        //each value is checked for presence and implicitly stated null as value
        boolean isPointsPresent = node.get("points").equals(NullNode.getInstance());
        if (isPointsPresent) {
            throw new IllegalArgumentException("Required property is null");
        }
        //check whether passed int isn't a char/String
        try {
            Integer.parseInt(node.get("points").toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Required property is a character: " + e.getMessage());
        }
    }

    public static void validateConstructorsData(JSONArray node) {
        //each JSONObject is checked for presence and implicitly stated null as value
        for (int i = 0; i < node.length(); i++) {
            boolean isPointsPresent = node.getJSONObject(i).get("points").equals(NullNode.getInstance());
            if (isPointsPresent) {
                throw new IllegalArgumentException("Required property is null");
            }
            //check whether passed int isn't a char/String
            try {
                Integer.parseInt(node.getJSONObject(i).get("points").toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Required property is a character: " + e.getMessage());
            }
        }
    }
}
