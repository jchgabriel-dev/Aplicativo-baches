package com.romzc.bachesapp.viewmodel

import androidx.lifecycle.LiveData
import com.romzc.bachesapp.data.entities.SeverityEntity
import com.romzc.bachesapp.data.entities.UserWithPotholes
import com.romzc.bachesapp.data.model.SeverityDao

class SeverityRepository(private val severityDao: SeverityDao) {

    val getAllUSeverity: LiveData<List<SeverityEntity>> = severityDao.getAllSeverity()

    suspend fun addSeverity(severity: SeverityEntity){
        severityDao.addSeverity(severity)
    }

    suspend fun getUserWithPotholes(): List<SeverityEntity>{
        return severityDao.getAllSev();
    }

}