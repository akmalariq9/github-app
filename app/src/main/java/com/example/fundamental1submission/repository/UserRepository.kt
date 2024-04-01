package com.example.fundamental1submission.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fundamental1submission.MainUser
import com.example.fundamental1submission.database.UserDao
import com.example.fundamental1submission.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<MainUser>> = mUsersDao.getAllUsers()

    fun insert(user: MainUser) {
        executorService.execute { mUsersDao.insert(user) }
    }

    fun delete(user: MainUser) {
        executorService.execute { mUsersDao.delete(user) }
    }
}