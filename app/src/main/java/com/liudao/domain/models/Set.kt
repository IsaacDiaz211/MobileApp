package com.liudao.domain.models

data class Set(
    val id: Long = 0,
    val exerciseId: Long,
    val routineId: Long,
    val weight: Float,
    val reps: Int,
    val order: Int
)