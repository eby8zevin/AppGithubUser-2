package com.ahmadabuhasan.appgithubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadabuhasan.appgithubuser.model.Follow;
import com.ahmadabuhasan.appgithubuser.model.Search;
import com.ahmadabuhasan.appgithubuser.model.SearchData;
import com.ahmadabuhasan.appgithubuser.model.User;
import com.ahmadabuhasan.appgithubuser.networking.ApiConfig;
import com.ahmadabuhasan.appgithubuser.networking.ApiService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private final String TAG = "UserViewModel";

    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<SearchData>> searchMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Follow>> followersMutableLiveData = new MutableLiveData<>();

    public void setUserDetail(String dataUser) {
        try {
            ApiService apiService = ApiConfig.getApiService();
            Call<User> call = apiService.detailUser(dataUser);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                    if (!response.isSuccessful()) {
                        Log.e("response", response.toString());
                    } else if (response.body() != null) {
                        userMutableLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                    Log.e("failure", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Token e", String.valueOf(e));
        }
    }

    public LiveData<User> getUserDetail() {
        return userMutableLiveData;
    }

    public void setSearchUser(String query) {
        try {
            //String ApiGithub = BuildConfig.TOKEN;
            ApiService apiService = ApiConfig.getApiService();
            Call<Search> call = apiService.searchUser(query);
            call.enqueue(new Callback<Search>() {
                @Override
                public void onResponse(@NotNull Call<Search> call, @NotNull Response<Search> response) {
                    if (!response.isSuccessful()) {
                        Log.e(TAG, response.toString());
                    } else if (response.body() != null) {
                        ArrayList<SearchData> user = new ArrayList<>(response.body().getSearchData());
                        searchMutableLiveData.setValue(user);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Search> call, @NotNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Token e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<SearchData>> getSearchData() {
        return searchMutableLiveData;
    }

    public void setFollowers(String dataFollowers) {
        try {
            ApiService apiService = ApiConfig.getApiService();
            Call<ArrayList<Follow>> call = apiService.followersUser(dataFollowers);
            call.enqueue(new Callback<ArrayList<Follow>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<Follow>> call, @NotNull Response<ArrayList<Follow>> response) {
                    if (!response.isSuccessful()) {
                        Log.e(TAG, response.toString());
                    } else if (response.body() != null) {
                        followersMutableLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<Follow>> call, @NotNull Throwable t) {
                    Log.e(TAG, toString());
                }
            });
        } catch (Exception e) {
            Log.d("Token e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<Follow>> getFollowersUser(){
        return followersMutableLiveData;
    }

}