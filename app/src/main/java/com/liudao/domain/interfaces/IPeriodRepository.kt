package com.liudao.domain.interfaces

import com.liudao.domain.models.Period
import kotlinx.coroutines.flow.Flow

interface IPeriodRepository {
    fun getAll(): Flow<List<Period>>
    suspend fun insert(period: Period): Long
    suspend fun delete(period: Period)
    fun getPeriodsBySupp(supplement: Long): Flow<List<Period>>
    suspend fun update(period: Period)
}