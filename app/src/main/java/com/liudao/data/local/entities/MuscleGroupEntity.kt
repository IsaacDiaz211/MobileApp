package com.liudao.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups")
data class MuscleGroupEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val name: String
)