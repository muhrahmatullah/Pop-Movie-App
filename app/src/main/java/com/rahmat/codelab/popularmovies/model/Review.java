package com.rahmat.codelab.popularmovies.model;

import java.util.List;

/**
 * Created by rahmat on 7/31/2017.
 */

public class Review {

    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<ReviewList> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ReviewList> getResults() {
        return results;
    }

    public void setResults(List<ReviewList> results) {
        this.results = results;
    }

    private static class ResultsBean {
    }
}
