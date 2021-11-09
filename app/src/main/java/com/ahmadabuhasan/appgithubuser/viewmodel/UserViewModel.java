package com.ahmadabuhasan.appgithubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadabuhasan.appgithubuser.BuildConfig;
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

    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<SearchData>> searchMutableLiveData = new MutableLiveData<>();

    public void setSearchUser(String query) {
        try {
            String ApiGithub = BuildConfig.TOKEN;
            ApiService apiService = ApiConfig.getApiService();
            Call<Search> call = apiService.searchUser(ApiGithub, query);
            call.enqueue(new Callback<Search>() {
                @Override
                public void onResponse(@NotNull Call<Search> call, @NotNull Response<Search> response) {
                    if (!response.isSuccessful()) {
                        Log.e("response", response.toString());
                    } else if (response.body() != null) {
                        ArrayList<SearchData> user = new ArrayList<>(response.body().getSearchData());
                        searchMutableLiveData.setValue(user);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Search> call, @NotNull Throwable t) {
                    Log.e("failure", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Token e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<SearchData>> getSearchData() {
        return searchMutableLiveData;
    }

}