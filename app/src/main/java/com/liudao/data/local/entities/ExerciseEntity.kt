package com.liudao.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "exercises",
    foreignKeys = [ForeignKey(
        entity = MuscleGroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["idMuscleGroup"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["idMuscleGroup"])]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val idMuscleGroup: Long
)