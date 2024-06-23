package com.romzc.bachesapp.viewmodel
import androidx.lifecycle.LiveData
import com.romzc.bachesapp.data.entities.UserEntity
import com.romzc.bachesapp.data.entities.UserWithPotholes
import com.romzc.bachesapp.data.model.UserDao


class UserRepository(private val userDao: UserDao) {

    val getAllUser: LiveData<List<UserEntity>> = userDao.getAllUser()

    suspend fun checkUser(email: String, password: String): Boolean {
        return userDao.checkUser(email, password)
    }

    suspend fun addUser(user: UserEntity){
        userDao.addUser(user)
    }

    suspend fun getUserWithPotholes(id: Int): List<UserWithPotholes>{
        return userDao.getUserWithPotholes(id);
    }

    suspend fun getUserName(userId: Int): String? {
        return userDao.getUserName(userId)
    }

    suspend fun getUserId(email: String, password: String): Int? {
        return userDao.getUserId(email, password)
    }
}