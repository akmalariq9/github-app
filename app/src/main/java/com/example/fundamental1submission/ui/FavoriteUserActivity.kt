package com.example.fundamental1submission.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamental1submission.MainUser
import com.example.fundamental1submission.R
import com.example.fundamental1submission.UsersAdapter
import com.example.fundamental1submission.databinding.ActivityFavoriteUserBinding
import com.example.fundamental1submission.viewModel.UserFavoriteViewModel
import com.example.fundamental1submission.viewModel.UserFavoriteViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private var _activityFavUserBinding: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        actionBar?.title = resources.getString(R.string.FavoriteList)

        binding?.rvUsersFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsersFavorite?.setHasFixedSize(true)

        val favoriteUserViewModel = obtainViewModel(this)
        favoriteUserViewModel.getAllUsers().observe(this) { userList ->
            if (userList.isNotEmpty()) {
                listData(userList)
            } else {
                binding?.rvUsersFavorite?.visibility = View.GONE
                binding?.tvNull?.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserFavoriteViewModel {
        val factory = UserFavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UserFavoriteViewModel::class.java]
    }

    private fun addUserSelected(data: MainUser) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(intent)
    }

    private fun listData(githubList: List<MainUser>) {
        val adapter = UsersAdapter(githubList)
        binding?.rvUsersFavorite?.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MainUser) {
                addUserSelected(data)
            }
        })
    }
}