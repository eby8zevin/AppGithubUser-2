package com.ahmadabuhasan.appgithubuser.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ahmadabuhasan.appgithubuser.R;
import com.ahmadabuhasan.appgithubuser.adapter.ViewPagerAdapter;
import com.ahmadabuhasan.appgithubuser.databinding.ActivityUserDetailBinding;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;

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

        getSupportActionBar().setHomeButtonEnabled(true);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}