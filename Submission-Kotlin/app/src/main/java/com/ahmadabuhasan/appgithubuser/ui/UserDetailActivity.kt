package com.ahmadabuhasan.appgithubuser.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ahmadabuhasan.appgithubuser.R
import com.ahmadabuhasan.appgithubuser.databinding.ActivityUserDetailBinding
import com.ahmadabuhasan.appgithubuser.model.UserResponse
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }
    /*
    Penulisan companion object sebaiknya diletakkan di paling bawah dari sebuah class sesuai dengan konvensi penulisan kode Kotlin di tautan https://kotlinlang.org/docs/reference/coding-conventions.html#class-layout.
     */

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        /*
        Hindari penggunaan hardcoded string. Kamu bisa memindahkannya kedalam file strings.xml.
         */
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        val user = intent.getParcelableExtra<UserResponse>(EXTRA_USER)
        if (user != null) {
            Glide.with(this)
                .load(user.avatar)
                .skipMemoryCache(true)
                .into(binding.imgAvatar)

            binding.tvName.text = user.name
            binding.tvUsername.text = buildString {
                append("@ ")
                append(user.username)
            }
            binding.tvCompany.text = user.company
            binding.tvLocation.text = user.location
            binding.tvRepositoryValue.text = user.repository.toString()
            binding.tvFollowersValue.text = user.follower.toString()
            binding.tvFollowingValue.text = user.following.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}