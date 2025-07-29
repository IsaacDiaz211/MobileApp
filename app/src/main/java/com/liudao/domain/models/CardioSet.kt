package com.liudao.domain.models

class CardioSet(
    val id: Long =0,
    val exerciseId: Long,
    val routineId: Long,
    val minutes: Int,
    val order: Int
)