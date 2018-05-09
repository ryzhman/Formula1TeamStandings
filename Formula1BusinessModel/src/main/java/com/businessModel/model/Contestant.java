package com.businessModel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Oleksandr Ryzhkov on 28.04.2018.
 */
@JsonPropertyOrder({"position", "points"})
public abstract class Contestant {
    @JsonProperty
    short points;
    @JsonProperty
    byte position;

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }
}
