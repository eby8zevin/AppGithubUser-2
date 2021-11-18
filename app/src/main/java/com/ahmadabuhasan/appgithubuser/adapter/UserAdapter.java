package com.ahmadabuhasan.appgithubuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadabuhasan.appgithubuser.databinding.ItemUserBinding;
import com.ahmadabuhasan.appgithubuser.model.SearchData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {

    private final ArrayList<SearchData> searchData = new ArrayList<>();
    private ArrayList<SearchData> searchDataFilter;
    public static Context context;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public UserAdapter(Context context) {
        UserAdapter.context = context;
    }

    public void setSearchData(ArrayList<SearchData> data) {
        searchData.clear();
        searchData.addAll(data);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        SearchData user = searchData.get(position);
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatarUrl())
                .apply(new RequestOptions().override(350, 350))
                .into(holder.binding.imgAvatar);
        holder.binding.tvHtml.setText(user.getHtmlUrl());
        holder.binding.tvUsername.setText(String.format("@%s", user.getUsername()));

        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(searchData.get(position)));
    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public UserViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(SearchData data);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchDataFilter = searchData;
                } else {
                    ArrayList<SearchData> filterList = new ArrayList<>();
                    for (SearchData row : searchData) {
                        if (row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(row);
                        }
                    }
                    searchDataFilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = searchDataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchDataFilter = (ArrayList<SearchData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}