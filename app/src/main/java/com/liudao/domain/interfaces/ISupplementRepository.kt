package com.liudao.domain.interfaces

import com.liudao.domain.models.Supplement
import kotlinx.coroutines.flow.Flow

interface ISupplementRepository {
    fun getAll(): Flow<List<Supplement>>
    suspend fun insert(sup: Supplement): Long
    suspend fun delete(sup: Supplement)
    suspend fun update(sup: Supplement)
    suspend fun getById(id: Long): Supplement?
}