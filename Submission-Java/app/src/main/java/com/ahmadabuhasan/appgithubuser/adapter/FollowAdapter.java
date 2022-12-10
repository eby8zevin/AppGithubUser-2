package com.ahmadabuhasan.appgithubuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadabuhasan.appgithubuser.databinding.ItemUserBinding;
import com.ahmadabuhasan.appgithubuser.model.Follow;
import com.ahmadabuhasan.appgithubuser.ui.UserDetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.ahmadabuhasan.appgithubuser.ui.UserDetailActivity.DETAIL_USER;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {

    private final ArrayList<Follow> followList = new ArrayList<>();
    Context context;

    public FollowAdapter(Context context) {
        this.context = context;
    }

    public void setFollowList(ArrayList<Follow> data) {
        followList.clear();
        followList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.ViewHolder holder, int position) {
        Follow follow = followList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(follow.getAvatarUrl())
                .into(holder.binding.imgAvatar);
        holder.binding.tvHtml.setText(follow.getHtmlUrl());
        holder.binding.tvUsername.setText(String.format("@%s", follow.getUsername()));

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, UserDetailActivity.class);
            i.putExtra(DETAIL_USER, follow.getUsername());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return followList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}