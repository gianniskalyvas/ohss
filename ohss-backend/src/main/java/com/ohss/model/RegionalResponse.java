package com.ohss.model;

import java.util.HashMap;
import java.util.Map;

public class RegionalResponse {


    private Map<String, Object> data = new HashMap<>();


    // getters and setters
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
