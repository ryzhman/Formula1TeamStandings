package com.formula1.standings.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleksandr Ryzhkov on 25.04.2018.
 */
public class ObjectWrapper {
    private String data;

    public ObjectWrapper() {
    }

    public ObjectWrapper(String result) {
        this.data = result;
    }

    @JsonProperty
    public String getData() {
        return data;
    }

    @JsonProperty
    public void setData(String data) {
        this.data = data;
    }
}
