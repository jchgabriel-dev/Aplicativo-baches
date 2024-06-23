package com.romzc.bachesapp.data.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Severity")
data class SeverityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SevId")
    val sevId: Int = 0,

    @ColumnInfo(name = "SevNam")
    val sevName: String,
)