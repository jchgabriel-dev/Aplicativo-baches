package com.romzc.bachesapp.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.romzc.bachesapp.data.entities.PotholeEntity
import com.romzc.bachesapp.data.entities.UserEntity

@Dao
interface PotholeDao {
    @Query("SELECT * FROM Pothole ORDER BY PotId")
    fun getAllPothole() : LiveData<List<PotholeEntity>>

    @Query("SELECT * FROM Pothole WHERE PotId = :id")
    suspend fun getPotholeById(id: Int): PotholeEntity

    @Insert
    suspend fun addPothole(pothole: PotholeEntity)

    @Delete
    suspend fun deletePothole(pothole: PotholeEntity)

    @Update
    suspend fun updatePothole(pothole: PotholeEntity)

}