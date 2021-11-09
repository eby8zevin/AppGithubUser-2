package com.ahmadabuhasan.appgithubuser.networking;

import com.ahmadabuhasan.appgithubuser.model.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    /*@GET("users/{username}")
    Call<ModelUser> detailUser(@Path("username") String username);*/

    @Headers({"Authorization: token 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"})
    @GET("/search/users")
    Call<Search> searchUser(
            @Query("q") String username);

    /*@GET("users/{username}/followers")
    Call<List<ModelFollow>> followersUser(@Headers("Authorization") String authorization,
                                          @Path("username") String username);*/
}