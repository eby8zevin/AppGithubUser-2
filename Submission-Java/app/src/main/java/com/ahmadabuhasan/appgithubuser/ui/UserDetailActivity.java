package com.ahmadabuhasan.appgithubuser.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ahmadabuhasan.appgithubuser.R;
import com.ahmadabuhasan.appgithubuser.adapter.ViewPagerAdapter;
import com.ahmadabuhasan.appgithubuser.databinding.ActivityUserDetailBinding;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class UserDetailActivity extends AppCompatActivity {

    private ActivityUserDetailBinding binding;
    public static String dataUser;
    public static String DETAIL_USER = "DETAIL_USER";
    UserViewModel userViewModel;
    private final int[] TAB_CLICK = new int[]{
            R.string.followers,
            R.string.following
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setTitle(R.string.detail_user);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(binding.tabsLayout, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_CLICK[position]))
        ).attach();

        dataUser = getIntent().getStringExtra(DETAIL_USER);
        showViewModel();
        userViewModel.isLoading().observe(this, this::showLoading);
    }

    private void showViewModel() {
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.setUserDetail(dataUser);
        userViewModel.getUserDetail().observe(this, user -> {
            Glide.with(getApplicationContext())
                    .load(user.getAvatarUrl())
                    .into(binding.imgUser);
            binding.tvNameDetail.setText(user.getName());
            binding.tvUsernameDetail.setText(String.format("@%s", user.getUsername()));
            binding.tvCompanyDetail.setText(String.format("%s%s", getResources().getString(R.string.company), user.getCompany()));
            binding.tvLocationDetail.setText(String.format("%s%s", getResources().getString(R.string.location), user.getLocation()));
            binding.tvBlogDetail.setText(String.format("%s%s", getResources().getString(R.string.blog), user.getHtmlUrl()));
            binding.tvFollowers.setText(user.getFollowers());
            binding.tvRepository.setText(user.getRepository());
            binding.tvFollowing.setText(user.getFollowing());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.share) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://github.com/" + dataUser));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}