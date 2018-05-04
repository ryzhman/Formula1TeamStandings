package com.businessModel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleksandr Ryzhkov on 28.04.2018.
 */
public abstract class Contestant {
    @JsonProperty
    int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
