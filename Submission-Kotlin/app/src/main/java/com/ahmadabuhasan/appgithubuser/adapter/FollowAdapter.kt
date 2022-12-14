package com.ahmadabuhasan.appgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmadabuhasan.appgithubuser.databinding.ItemsUserBinding
import com.ahmadabuhasan.appgithubuser.model.ResponseFollow
import com.bumptech.glide.Glide

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    private val listUser = ArrayList<ResponseFollow>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(data: ArrayList<ResponseFollow>) {
        val diffCallback = DiffUtilCallback(listUser, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUser.clear()
        listUser.addAll(data)
        diffResult.dispatchUpdatesTo(this)
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

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ListViewHolder(private val _binding: ItemsUserBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(user: ResponseFollow) {
            _binding.tvAccount.text = user.htmlUrl
            _binding.tvUsername.text = user.login

            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .skipMemoryCache(true)
                .into(_binding.imgAvatar)
        }
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(data: ResponseFollow)
    }

    class DiffUtilCallback(
        private val oldList: List<ResponseFollow>,
        private val newList: List<ResponseFollow>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.javaClass == newItem.javaClass
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.hashCode() == newItem.hashCode()
        }

        @Override
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}