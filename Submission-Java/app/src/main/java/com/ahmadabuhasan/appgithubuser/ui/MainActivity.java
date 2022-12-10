package com.ahmadabuhasan.appgithubuser.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.appgithubuser.R;
import com.ahmadabuhasan.appgithubuser.adapter.UserAdapter;
import com.ahmadabuhasan.appgithubuser.databinding.ActivityMainBinding;
import com.ahmadabuhasan.appgithubuser.model.SearchData;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;

import es.dmoral.toasty.Toasty;

import static com.ahmadabuhasan.appgithubuser.ui.UserDetailActivity.DETAIL_USER;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
    private UserAdapter userAdapter;
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.users_search);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.NoData.setVisibility(View.VISIBLE);
        binding.rvGithub.setHasFixedSize(true);
        showViewModel();
        showRecyclerView();
        userViewModel.isLoading().observe(this, this::showLoading);
    }

    private void showViewModel() {
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.getSearchData().observe(this, searchData -> {
            if (searchData.size() != 0) {
                binding.NoData.setVisibility(View.GONE);
                userAdapter.setSearchData(searchData);
            } else {
                binding.NoData.setVisibility(View.VISIBLE);
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

        Intent i = new Intent(UserAdapter.context, UserDetailActivity.class);
        i.putExtra(DETAIL_USER, user.getUsername());
        UserAdapter.context.startActivity(i);
        hideKeyboard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            MenuItem closeSearch = menu.findItem(R.id.search);
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
                    userViewModel.setSearchUser(newText);
                    return false;
                }
            });
            closeSearch.getIcon().setVisible(false, false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.refresh) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toasty.warning(this, "Press once again to exit", Toasty.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}