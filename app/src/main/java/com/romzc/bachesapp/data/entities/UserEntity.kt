package com.romzc.bachesapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UseId")
    val userId: Int = 0,

    @ColumnInfo(name = "UseNam")
    val userName: String,

    @ColumnInfo(name = "UsePas")
    val userPass: String,

    @ColumnInfo(name = "UseEma")
    val userEmai: String
)