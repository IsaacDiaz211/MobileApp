package com.liudao.domain.repositories

import com.liudao.data.local.dao.SetDao
import com.liudao.data.local.entities.SetEntity
import com.liudao.domain.interfaces.ISetRepository
import com.liudao.domain.models.Set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetRepository @Inject constructor(
    private val dao: SetDao
): ISetRepository {
    override fun getAll(): Flow<List<Set>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override fun getByRoutine(routine: Int): Flow<List<Set>> =
        dao.getSetsByRoutine(routine).map { list -> list.map { it.toDomain() } }

    override suspend fun insert(set: Set): Long =
        dao.insert(set.toEntity())

    override suspend fun insertAll(list: List<Set>) =
        dao.insertAll(list.map { it.toEntity() })

    override suspend fun delete(set: Set) =
        dao.delete(set.toEntity())
}

//Mappers
fun SetEntity.toDomain() = Set(id, exerciseId, routineId, weight, reps, order)
fun Set.toEntity() = SetEntity(id, exerciseId, routineId, weight, reps, order)