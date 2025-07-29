package com.liudao.domain.interfaces

import com.liudao.domain.models.CardioSet
import kotlinx.coroutines.flow.Flow

interface ICardioSetRepository {
    fun getAll(): Flow<List<CardioSet>>
    fun getByRoutine(routine: Int): Flow<List<CardioSet>>
    suspend fun insert(set: CardioSet): Long
    suspend fun insertAll(list: List<CardioSet>)
    suspend fun delete(set: CardioSet)
}