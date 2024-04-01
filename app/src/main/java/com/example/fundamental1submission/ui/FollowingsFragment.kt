package com.example.fundamental1submission.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamental1submission.UsersAdapter
import com.example.fundamental1submission.MainUser
import com.example.fundamental1submission.databinding.FragmentFollowingsBinding
import com.example.fundamental1submission.viewModel.DetailViewModel

class FollowingsFragment : Fragment() {

    private lateinit var binding: FragmentFollowingsBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowings.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowings.addItemDecoration(itemDecoration)
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        detailViewModel.allfollowings.observe(viewLifecycleOwner, {
            setUserFollowingData(it)
        })

        detailViewModel.isLoadingFollowing.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.hasNoFollowing.visibility = View.GONE
            binding.progressBarFollowing.visibility = View.VISIBLE
            binding.rvFollowings.alpha = 0.0F
        } else {
            binding.progressBarFollowing.visibility = View.GONE
            binding.rvFollowings.alpha = 1F
        }
    }

    private fun setUserFollowingData(user: List<MainUser>) {
        binding.hasNoFollowing.visibility = if (user.isEmpty()) View.VISIBLE else View.GONE
        val listUserAdapter = UsersAdapter(user)
        binding.rvFollowings.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MainUser) {
                detailViewModel.userlogin = data.login.toString()
            }
        })
    }
}
