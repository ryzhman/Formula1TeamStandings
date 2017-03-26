package com.formula1.standings.entity;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * Created by Oleksandr Ryzhkov on 25.03.2017.
 */
public class Constructor {
    private String id;
    private String title;
    private int points;

    public Constructor(String title) {
        this.title = title;
        this.points = 0;
    }

    public Constructor() {
    }

    ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "id='" + id +
                ", title='" + title +
                ", points=" + points;
    }
}
