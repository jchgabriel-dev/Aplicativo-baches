package com.romzc.bachesapp.data

import java.sql.Date


data class PotHole(
    val id:Int,
    val title: String,
    val description: String,
    val photoUri: String,
    val severity: Severity,
    val registerDate: Date,
    val position: GpsPosition,
    val user: User
)


data class GpsPosition(
    val id: Int,
    val latitude: Double,
    val longitude: Double
)


data class Severity(
    val id: Int,
    val severity: String
)


data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)