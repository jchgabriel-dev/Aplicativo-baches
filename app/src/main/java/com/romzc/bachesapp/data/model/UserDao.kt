package com.romzc.bachesapp.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.romzc.bachesapp.data.entities.UserEntity
import com.romzc.bachesapp.data.entities.UserWithPotholes


@Dao
interface UserDao {

    @Query("SELECT * FROM User ORDER BY UseId")
    fun getAllUser() : LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM User WHERE UseEma = :email AND UsePas = :password)")
    suspend fun checkUser(email: String, password: String): Boolean

    @Query("SELECT UseNam FROM User WHERE UseId = :userId")
    suspend fun getUserName(userId: Int): String?

    @Query("SELECT UseId FROM User WHERE UseEma = :email AND UsePas = :password")
    suspend fun getUserId(email: String, password: String): Int?

    @Insert
    suspend fun addUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM User WHERE UseId = :userId")
    suspend fun getUserWithPotholes(userId: Int): List<UserWithPotholes>
}