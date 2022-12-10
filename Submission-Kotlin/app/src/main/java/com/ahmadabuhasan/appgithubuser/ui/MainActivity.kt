package com.ahmadabuhasan.appgithubuser.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadabuhasan.appgithubuser.adapter.UserAdapter
import com.ahmadabuhasan.appgithubuser.databinding.ActivityMainBinding
import com.ahmadabuhasan.appgithubuser.model.UserResponse
import com.ahmadabuhasan.appgithubuser.utils.JsonHelper

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var jsonHelper: JsonHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsonHelper = JsonHelper(this)
        showRecyclerUser()
    }

    private fun showRecyclerUser() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }

        adapter = UserAdapter(jsonHelper.loadUsers() as ArrayList<UserResponse>)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                selectedUser(data)
            }
        })
    }

    private fun selectedUser(user: UserResponse) {
        Toast.makeText(this, "You choose ${user.name}", Toast.LENGTH_SHORT).show()

        val i = Intent(this, UserDetailActivity::class.java)
        i.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(i)
    }
}