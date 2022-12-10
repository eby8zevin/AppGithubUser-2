package com.ahmadabuhasan.appgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadabuhasan.appgithubuser.databinding.ItemsUserBinding
import com.ahmadabuhasan.appgithubuser.model.UserResponse
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: ArrayList<UserResponse>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemsUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listUser[position]
            )
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(private val _binding: ItemsUserBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(user: UserResponse) {
            _binding.tvName.text = user.name
            _binding.tvUsername.text = user.username
            _binding.tvLocation.text = user.location

            Glide.with(itemView.context)
                .load(user.avatar)
                .skipMemoryCache(true)
                .into(_binding.imgAvatar)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }
}