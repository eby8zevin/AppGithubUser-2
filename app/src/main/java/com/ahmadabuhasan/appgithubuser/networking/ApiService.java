package com.ahmadabuhasan.appgithubuser.networking;

import com.ahmadabuhasan.appgithubuser.model.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search/users")
    Call<Search> searchUser(@Header("Authorization") String authorization,
                          @Query("q") String username);

}