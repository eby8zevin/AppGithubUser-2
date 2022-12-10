package com.ahmadabuhasan.appgithubuser.utils

import android.content.Context
import com.ahmadabuhasan.appgithubuser.model.UserResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingJSON(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            null
        }
    }

    fun loadUsers(): List<UserResponse> {
        val list = ArrayList<UserResponse>()
        try {
            val responseObject = JSONObject(parsingJSON("githubuser.json").toString())
            val listArray = responseObject.getJSONArray("users")
            for (i in 0 until listArray.length()) {
                val user = listArray.getJSONObject(i)

                val username = user.getString("username")
                val name = user.getString("name")
                val avatar = user.getString("avatar")
                val company = user.getString("company")
                val location = user.getString("location")
                val repository = user.getInt("repository")
                val follower = user.getInt("follower")
                val following = user.getInt("following")

                val convert: Int =
                    context.resources.getIdentifier(avatar, null, context.packageName)

                val userResponse = UserResponse(
                    username,
                    name,
                    convert,
                    company,
                    location,
                    repository,
                    follower,
                    following
                )
                list.add(userResponse)
            }
        } catch (jsonE: JSONException) {
            jsonE.printStackTrace()
        }
        return list
    }
}