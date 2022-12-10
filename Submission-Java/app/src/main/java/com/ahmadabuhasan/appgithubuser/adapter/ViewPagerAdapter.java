package com.ahmadabuhasan.appgithubuser.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ahmadabuhasan.appgithubuser.ui.FollowersFragment;
import com.ahmadabuhasan.appgithubuser.ui.FollowingFragment;

import java.util.Objects;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FollowersFragment();
                break;
            case 1:
                fragment = new FollowingFragment();
                break;
        }
        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}