package com.ahmadabuhasan.appgithubuser.networking;

import com.ahmadabuhasan.appgithubuser.model.Search;
import com.ahmadabuhasan.appgithubuser.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /*@Headers({"Authorization: token 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"})*/
    @GET("/search/users")
    Call<Search> searchUser(
            @Query("q") String username);

   /* @GET("/search/users")
    Call<Search> searchUser(@Header("Authorization") String authorization,
                            @Query("q") String username);*/

    @GET("users/{username}")
    Call<User> detailUser(
            @Path("username") String username);
}