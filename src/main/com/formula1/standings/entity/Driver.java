package com.formula1.standings.entity;

import com.formula1.standings.utils.CountryUtil;

import javax.persistence.OneToMany;
import java.util.Locale;

/**
 * Created by Oleksandr Ryzhkov on 25.03.2017.
 */
public class Driver {
    private String id;
    private String name;
    private String nationality;
    private int wins;
    private int points;
    private String teamTitle;

    public Driver(String name, String nationality, String teamTitle) {
        this.name = name;
        this.nationality = nationality;
        this.teamTitle = teamTitle;
        this.points = 0;
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

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }

    @Override
    public String toString() {
        return "id='" + id +
                ", name='" + name +
                ", nationality='" + nationality +
                ", wins=" + wins +
                ", points=" + points +
                ", teamTitle='" + teamTitle;
    }
}
