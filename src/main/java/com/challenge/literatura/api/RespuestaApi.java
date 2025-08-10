package com.challenge.literatura.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaApi {

    private int count;
    private List<BookData> results;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BookData> getResults() {
        return results;
    }

    public void setResults(List<BookData> results) {
        this.results = results;
    }
}
