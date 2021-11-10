package com.ahmadabuhasan.appgithubuser.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.appgithubuser.R;
import com.ahmadabuhasan.appgithubuser.adapter.UserAdapter;
import com.ahmadabuhasan.appgithubuser.databinding.ActivityMainBinding;
import com.ahmadabuhasan.appgithubuser.model.SearchData;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private UserViewModel userViewModel;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.users_search);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvGithub.setHasFixedSize(true);
        showViewModel();
        showRecyclerView();

        userViewModel.getSearchData().observe(this, new Observer<ArrayList<SearchData>>() {
            @Override
            public void onChanged(ArrayList<SearchData> searchData) {
                if (searchData.size() != 0) {
                    userAdapter.setSearchData(searchData);
                } else {
                    Toasty.warning(getApplicationContext(), "No Result", Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showViewModel() {
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.getSearchData().observe(this, searchData -> {
            showLoading(false);
            if (searchData.size() != 0) {
                userAdapter.setSearchData(searchData);
            } else {
                Toasty.warning(getApplicationContext(), "User Not Found!", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void showRecyclerView() {
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvGithub.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            binding.rvGithub.setLayoutManager(new LinearLayoutManager(this));
        }

        userAdapter = new UserAdapter(this);
        userAdapter.notifyDataSetChanged();
        binding.rvGithub.setAdapter(userAdapter);

        userAdapter.setOnItemClickCallback(this::showSelectedUser);
    }

    private void showSelectedUser(SearchData user) {
        Toasty.success(this, "You choose " + user.getUsername(), Toasty.LENGTH_SHORT).show();

        /*Intent i = new Intent(getApplicationContext(), UserDetailActivity.class);
        getApplicationContext().startActivity(i);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            showLoading(true);
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    userViewModel.setSearchUser(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (userViewModel != null) {
                        userViewModel.setSearchUser(newText);
                    }
                    return false;
                }
            });
        }
        return true;
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}