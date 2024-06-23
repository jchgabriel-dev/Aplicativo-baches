package com.romzc.bachesapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Pothole")
data class PotholeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PotId")
    val potId: Int = 0,

    @ColumnInfo(name = "PotUse")
    val potUser: Int,

    @ColumnInfo(name = "PotSev")
    val potSev: Int,

    @ColumnInfo(name = "PotDes")
    val potDesc: String,

    @ColumnInfo(name = "PotImg")
    val potImg: String,

    @ColumnInfo(name="PotDate")
    val potDate: String = SimpleDateFormat("dd/MM/yyyy_HHmmss", Locale.getDefault()).format(Date())
)