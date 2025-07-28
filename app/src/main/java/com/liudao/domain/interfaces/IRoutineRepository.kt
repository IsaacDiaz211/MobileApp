package com.liudao.domain.interfaces

import com.liudao.domain.models.Routine
import kotlinx.coroutines.flow.Flow

interface IRoutineRepository {
    fun getAll(): Flow<List<Routine>>
    suspend fun insert(routine: Routine): Long
    suspend fun delete(routine: Routine)
}