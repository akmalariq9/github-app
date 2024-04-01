package com.example.fundamental1submission.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamental1submission.BuildConfig
import com.example.fundamental1submission.data.ApiConfig
import com.example.fundamental1submission.DetailUser
import com.example.fundamental1submission.MainUser
import com.example.fundamental1submission.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _detailuser = MutableLiveData<DetailUser>()
    val detailuser: LiveData<DetailUser> = _detailuser

    private val _allfollowers = MutableLiveData<List<MainUser>>()
    val allfollowers: LiveData<List<MainUser>> = _allfollowers

    private val _allfollowings = MutableLiveData<List<MainUser>>()
    val allfollowings: LiveData<List<MainUser>> = _allfollowings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<MainUser>> = mUserRepository.getAllUsers()

    fun insert(user: MainUser) {
        mUserRepository.insert(user)
    }

    fun delete(user: MainUser) {
        mUserRepository.delete(user)
    }

    var userlogin: String = ""
        set(value) {
            field = value
            getDetailUser()
            getDetailUserFollowers()
            getDetailUserFollowings()
        }

    private fun getDetailUser() {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailUser(userlogin, BuildConfig.token)
        api.enqueue(object : retrofit2.Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailuser.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getDetailUserFollowers() {
        _isLoadingFollower.value = true
        val api = ApiConfig.getApiService().getAllFollowers(userlogin, BuildConfig.token)
        api.enqueue(object : retrofit2.Callback<List<MainUser>> {
            override fun onResponse(
                call: Call<List<MainUser>>,
                response: Response<List<MainUser>>
            ) {
                _isLoadingFollower.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _allfollowers.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MainUser>>, t: Throwable) {
                _isLoadingFollower.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getDetailUserFollowings() {
        _isLoadingFollowing.value = true
        val api = ApiConfig.getApiService().getAllFollowings(userlogin, BuildConfig.token)
        api.enqueue(object : retrofit2.Callback<List<MainUser>> {
            override fun onResponse(
                call: Call<List<MainUser>>,
                response: Response<List<MainUser>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _allfollowings.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MainUser>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
