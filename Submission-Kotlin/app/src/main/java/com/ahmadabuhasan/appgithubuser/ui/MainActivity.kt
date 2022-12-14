package com.ahmadabuhasan.appgithubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadabuhasan.appgithubuser.R
import com.ahmadabuhasan.appgithubuser.adapter.UserAdapter
import com.ahmadabuhasan.appgithubuser.databinding.ActivityMainBinding
import com.ahmadabuhasan.appgithubuser.model.Items
import com.ahmadabuhasan.appgithubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter = UserAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViewModel()
        showRecyclerView()
        viewModel.getIsLoading.observe(this, this::showLoading)
    }

    private fun showViewModel() {
        viewModel.getSearchList.observe(this) { searchList ->
            if (searchList.size != 0) {
                binding.tvNotFound.visibility = View.GONE
                binding.rvUser.visibility = View.VISIBLE
                adapter.setData(searchList)
            } else {
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvUser.visibility = View.GONE
                Toast.makeText(this, "User Not Found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerView() {
        binding.rvUser.layoutManager =
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(this, 2)
            } else {
                LinearLayoutManager(this)
            }

        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }

    private fun selectedUser(user: Items) {
        Toast.makeText(this, "You choose ${user.login}", Toast.LENGTH_SHORT).show()

        val i = Intent(this, UserDetailActivity::class.java)
        i.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val close = menu.findItem(R.id.search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        close.icon.setVisible(false, false)

        return true
    }

    private fun showLoading(isLoading: Boolean) =
        binding.progressBar.visibility == if (isLoading) View.VISIBLE else View.GONE
}