package com.ahmadabuhasan.appgithubuser.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.appgithubuser.R;
import com.ahmadabuhasan.appgithubuser.adapter.UserAdapter;
import com.ahmadabuhasan.appgithubuser.databinding.ActivityMainBinding;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;

    private UserViewModel userViewModel;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.users_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading . . .");

        binding.rvGithub.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        userAdapter.notifyDataSetChanged();
        binding.rvGithub.setAdapter(userAdapter);
        binding.rvGithub.setHasFixedSize(true);


        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.getResultList().observe(this, searchData -> {
            progressDialog.dismiss();
            if (searchData.size() != 0) {
                userAdapter.setSearchData(searchData);
            } else {
                Toasty.warning(getApplicationContext(), "User Not Found!", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
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
}