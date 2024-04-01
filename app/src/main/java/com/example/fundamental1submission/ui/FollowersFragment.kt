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
import com.example.fundamental1submission.databinding.FragmentFollowersBinding
import com.example.fundamental1submission.viewModel.DetailViewModel

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        detailViewModel.allfollowers.observe(viewLifecycleOwner, {
            setUserFollowerData(it)
        })

        detailViewModel.isLoadingFollower.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.hasNoFollowers.visibility = View.GONE
            binding.progressBarFollower.visibility = View.VISIBLE
            binding.rvFollowers.alpha = 0.0F
        } else {
            binding.progressBarFollower.visibility = View.GONE
            binding.rvFollowers.alpha = 1F
        }
    }

    private fun setUserFollowerData(user: List<MainUser>) {
        binding.hasNoFollowers.visibility = if (user.isEmpty()) View.VISIBLE else View.GONE
        val listUserAdapter = UsersAdapter(user)
        binding.rvFollowers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MainUser) {
                detailViewModel.userlogin = data.login.toString()
            }
        })
    }
}
