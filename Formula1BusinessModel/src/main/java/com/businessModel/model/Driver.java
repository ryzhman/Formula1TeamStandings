package com.businessModel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.businessModel.utils.CountryUtil;

/**
 * Created by Oleksandr Ryzhkov on 25.03.2017.
 */
public class Driver extends Contestant {
    @JsonProperty
    private String name;
    @JsonProperty
    private String nationality;
    @JsonProperty
    private int wins;
    @JsonProperty
    private Constructor team;

    public Driver(String name, String nationality, Constructor team) {
        setPoints(0);
        this.name = name;
        this.nationality = CountryUtil.getCountry(nationality);
        this.team = team;
        this.wins = 0;
    }

    public Driver() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = CountryUtil.getCountry(nationality);
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Constructor getTeam() {
        return team;
    }

    public void setTeam(Constructor team) {
        this.team = team;
    }


}
