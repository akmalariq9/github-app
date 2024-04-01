package com.example.fundamental1submission.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamental1submission.data.ApiConfig
import com.example.fundamental1submission.ui.MainActivity
import com.example.fundamental1submission.MainUser
import com.example.fundamental1submission.SearchUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<List<MainUser>>()
    val user: LiveData<List<MainUser>> = _user

    private val _listUser = MutableLiveData<List<MainUser>>()
    val listUser: LiveData<List<MainUser>> = _listUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getData()
    }

    fun replaceListUser() {
        _listUser.value = _user.value
    }

    fun getDataUserSearch(searchUser: String) {
        _isLoading.value = true
        val service = ApiConfig.getApiService().getUserSearch(searchUser, MainActivity.APICode)
        service.enqueue(object : Callback<SearchUsers> {
            override fun onResponse(call: Call<SearchUsers>, response: Response<SearchUsers>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getData() {
        _isLoading.value = true
        val service = ApiConfig.getApiService().getUsers(MainActivity.APICode)
        service.enqueue(object : Callback<List<MainUser>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<List<MainUser>>,
                response: Response<List<MainUser>>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _user.value = responseBody
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MainUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}