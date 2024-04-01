package com.example.fundamental1submission.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fundamental1submission.MainUser
import com.example.fundamental1submission.repository.UserRepository

class UserFavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<MainUser>> = mUserRepository.getAllUsers()
}