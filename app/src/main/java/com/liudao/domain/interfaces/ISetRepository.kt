package com.liudao.domain.interfaces

import com.liudao.domain.models.Set
import kotlinx.coroutines.flow.Flow

interface ISetRepository {
    fun getAll(): Flow<List<Set>>
    fun getByRoutine(routine: Int): Flow<List<Set>>
    suspend fun insert(set: Set): Long
    suspend fun insertAll(list: List<Set>)
    suspend fun delete(set: Set)
}