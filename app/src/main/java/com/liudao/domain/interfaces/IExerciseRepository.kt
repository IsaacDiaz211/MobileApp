package com.liudao.domain.interfaces

import com.liudao.domain.models.Exercise
import kotlinx.coroutines.flow.Flow

interface IExerciseRepository {
    fun getAll(): Flow<List<Exercise>>
    suspend fun insert(exercise: Exercise): Long
    suspend fun delete(exercise: Exercise)
    suspend fun search(text: String): Flow<List<Exercise>>
}