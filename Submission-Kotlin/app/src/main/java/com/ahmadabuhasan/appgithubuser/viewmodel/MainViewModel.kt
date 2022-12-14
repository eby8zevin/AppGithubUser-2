package com.ahmadabuhasan.appgithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadabuhasan.appgithubuser.api.ApiConfig
import com.ahmadabuhasan.appgithubuser.model.Items
import com.ahmadabuhasan.appgithubuser.model.ResponseDetailUser
import com.ahmadabuhasan.appgithubuser.model.ResponseFollow
import com.ahmadabuhasan.appgithubuser.model.ResponseSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val searchList = MutableLiveData<ArrayList<Items>>()
    val getSearchList: LiveData<ArrayList<Items>> = searchList

    private val userDetail = MutableLiveData<ResponseDetailUser>()
    val getUserDetail: LiveData<ResponseDetailUser> = userDetail

    private val followers = MutableLiveData<ArrayList<ResponseFollow>>()
    val getFollowers: LiveData<ArrayList<ResponseFollow>> = followers

    private val following = MutableLiveData<ArrayList<ResponseFollow>>()
    val getFollowing: LiveData<ArrayList<ResponseFollow>> = following

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    fun searchUser(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().search(username)
            client.enqueue(object : Callback<ResponseSearch> {
                override fun onResponse(
                    call: Call<ResponseSearch>,
                    response: Response<ResponseSearch>
                ) {
                    isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        searchList.value = ArrayList(responseBody.items)
                    }
                }

                override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun detailUser(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().detailUser(username)
            client.enqueue(object : Callback<ResponseDetailUser> {
                override fun onResponse(
                    call: Call<ResponseDetailUser>,
                    response: Response<ResponseDetailUser>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        userDetail.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun followers(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().followers(username)
            client.enqueue(object : Callback<ArrayList<ResponseFollow>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponseFollow>>,
                    response: Response<ArrayList<ResponseFollow>>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        followers.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseFollow>>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun following(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().following(username)
            client.enqueue(object : Callback<ArrayList<ResponseFollow>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponseFollow>>,
                    response: Response<ArrayList<ResponseFollow>>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        following.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseFollow>>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}