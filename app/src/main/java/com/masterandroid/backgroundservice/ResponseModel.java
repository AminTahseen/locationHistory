package com.masterandroid.backgroundservice;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("results")
    private List<place> results;

    @SerializedName("status")
    private String status;

    public List<place> getResults() {
        return results;
    }

    public void setResults(List<place> results) {
        this.results = results;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
