package com.liudao.domain.repositories

import com.liudao.data.local.dao.RoutineDao
import com.liudao.data.local.entities.RoutineEntity
import com.liudao.domain.interfaces.IRoutineRepository
import com.liudao.domain.models.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val dao: RoutineDao
): IRoutineRepository {
    override fun getAll(): Flow<List<Routine>> =
        dao.getRoutines().map { list -> list.map { it.toDomain() } }

    override suspend fun insert(routine: Routine): Long =
        dao.insert(routine.toEntity())

    override suspend fun delete(routine: Routine) =
        dao.delete(routine.toEntity())
}

//Mappers
fun RoutineEntity.toDomain() = Routine(id, date)
fun Routine.toEntity() = RoutineEntity(id, date)