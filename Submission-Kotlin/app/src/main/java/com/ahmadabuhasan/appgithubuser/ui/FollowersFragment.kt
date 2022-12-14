package com.ahmadabuhasan.appgithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadabuhasan.appgithubuser.adapter.FollowAdapter
import com.ahmadabuhasan.appgithubuser.databinding.FragmentFollowersBinding
import com.ahmadabuhasan.appgithubuser.model.ResponseFollow
import com.ahmadabuhasan.appgithubuser.viewmodel.MainViewModel

class FollowersFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter = FollowAdapter()

    private lateinit var binding: FragmentFollowersBinding
    private val _binding get() = binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showViewModel()
        showRecyclerView()
        viewModel.getIsLoading.observe(viewLifecycleOwner, this::showLoading)
    }

    private fun showViewModel() {
        viewModel.followers(UserDetailActivity.username)
        viewModel.getFollowers.observe(viewLifecycleOwner) { followers ->
            if (followers.size != 0) {
                adapter.setData(followers)
            } else {
                Toast.makeText(context, "Followers Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerView() {
        with(binding.rvFollowers) {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = adapter
        }

        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }

    private fun selectedUser(user: ResponseFollow) {
        Toast.makeText(context, "You choose ${user.login}", Toast.LENGTH_SHORT).show()

        val i = Intent(activity, UserDetailActivity::class.java)
        i.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(i)
    }

    private fun showLoading(isLoading: Boolean) =
        binding.progressBar.visibility == if (isLoading) View.VISIBLE else View.GONE

    override fun onResume() {
        super.onResume()
        viewModel.followers(UserDetailActivity.username)
    }
}