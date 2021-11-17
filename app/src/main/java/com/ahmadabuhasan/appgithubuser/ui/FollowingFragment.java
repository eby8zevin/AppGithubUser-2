package com.ahmadabuhasan.appgithubuser.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.appgithubuser.adapter.FollowAdapter;
import com.ahmadabuhasan.appgithubuser.databinding.FragmentFollowingBinding;
import com.ahmadabuhasan.appgithubuser.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;

public class FollowingFragment extends Fragment {

    FragmentFollowingBinding binding;
    private UserViewModel followingViewModel;
    private FollowAdapter followAdapter;

    public FollowingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        followAdapter = new FollowAdapter(getContext());
        followAdapter.notifyDataSetChanged();
        binding.rvFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFollowing.setAdapter(followAdapter);
        binding.rvFollowing.setHasFixedSize(true);

        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        followingViewModel.setFollowing(UserDetailActivity.dataUser);
        followingViewModel.getFollowingUser().observe(getViewLifecycleOwner(), follows -> {
            if (follows.size() != 0) {
                followAdapter.setFollowList(follows);
            } else {
                Toasty.info(requireContext(), "Following Not Found", Toasty.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        followingViewModel.setFollowers(UserDetailActivity.dataUser);
    }
}