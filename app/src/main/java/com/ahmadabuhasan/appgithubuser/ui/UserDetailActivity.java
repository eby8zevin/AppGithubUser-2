package com.ahmadabuhasan.appgithubuser.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ahmadabuhasan.appgithubuser.R;
import com.ahmadabuhasan.appgithubuser.databinding.ActivityUserDetailBinding;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;

public class UserDetailActivity extends AppCompatActivity {

    ActivityUserDetailBinding binding;
    public static String DETAIL_USER = "DETAIL_USER";
    UserViewModel userViewModel;
    String dataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setTitle(R.string.detail_user);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            binding.tvCompanyDetail.setText(user.getCompany());
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