package com.businessModel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleksandr Ryzhkov on 25.03.2017.
 */
public class Constructor extends Contestant {
    @JsonProperty
    private String title;

    public Constructor(String title) {
        setPoints(0);
        this.title = title;
    }

    public Constructor() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "id='" + id +
                ", title='" + title +
                ", points=" + points;
    }
}
