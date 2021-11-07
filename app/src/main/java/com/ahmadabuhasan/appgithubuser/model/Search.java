package com.ahmadabuhasan.appgithubuser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("items")
    private List<SearchData> searchData;

    public List<SearchData> getSearchData() {
        return searchData;
    }

    public void setSearchData(List<SearchData> searchData) {
        this.searchData = searchData;
    }

}