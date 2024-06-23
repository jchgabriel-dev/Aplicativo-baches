package com.romzc.bachesapp.data.model
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.romzc.bachesapp.data.entities.PotholeAndSeverity
import com.romzc.bachesapp.data.entities.PotholeEntity
import com.romzc.bachesapp.data.entities.SeverityEntity

@Dao
interface SeverityDao {
    @Query("SELECT * FROM Severity ORDER BY SevId")
    fun getAllSeverity() : LiveData<List<SeverityEntity>>

    @Query("SELECT * FROM Severity ORDER BY SevId")
    fun getAllSev() : List<SeverityEntity>

    @Insert
    suspend fun addSeverity(severityEntity: SeverityEntity)
}