package com.romzc.bachesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romzc.bachesapp.data.PotHole
import com.romzc.bachesapp.data.entities.PotholeEntity
import com.romzc.bachesapp.data.entities.UserWithPotholes
import com.romzc.bachesapp.data.model.PotholeDao
import com.romzc.bachesapp.data.model.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PotholeRepository(
    private val userDao: UserDao,
    private val potholeDao: PotholeDao,
    private val id: Int
) {

    private val _getAllPothole: MutableLiveData<List<UserWithPotholes>> = MutableLiveData()

    var getAllPothole: LiveData<List<UserWithPotholes>>  = _getAllPothole

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val userWithPotholes = userDao.getUserWithPotholes(1)
            _getAllPothole.postValue(userWithPotholes)
        }
    }

    suspend fun getPothole(id: Int): PotholeEntity{
        return potholeDao.getPotholeById(id)
    }

    suspend fun addPothole(pothole: PotholeEntity){
        potholeDao.addPothole(pothole)
    }

    suspend fun deletePothole(pothole: PotholeEntity){
        potholeDao.deletePothole(pothole)
    }

    suspend fun updatePothole(pothole: PotholeEntity){
        potholeDao.updatePothole(pothole)
    }
}