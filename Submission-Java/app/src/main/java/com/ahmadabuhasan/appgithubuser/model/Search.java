package com.ahmadabuhasan.appgithubuser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("items")
    private List<SearchData> searchData;

    public List<SearchData> getSearch() {
        return searchData;
    }

    public void setSearch(List<SearchData> searchData) {
        this.searchData = searchData;
    }
}