package com.romzc.bachesapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PotholeAndSeverity(
    @Embedded
    val pothole: PotholeEntity,

    @Relation(
        parentColumn = "PotSev",
        entityColumn = "SevId",
    )
    val severity: SeverityEntity
)
