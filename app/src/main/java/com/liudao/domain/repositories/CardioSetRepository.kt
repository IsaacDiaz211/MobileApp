package com.liudao.domain.repositories

import com.liudao.data.local.dao.CardioSetDao
import com.liudao.data.local.entities.CardioSetEntity
import com.liudao.domain.interfaces.ICardioSetRepository
import com.liudao.domain.models.CardioSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardioSetRepository @Inject constructor(
    private val dao: CardioSetDao
): ICardioSetRepository {
    override fun getAll(): Flow<List<CardioSet>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override fun getByRoutine(routine: Int): Flow<List<CardioSet>> =
        dao.getCardioSetsByRoutine(routine).map { list -> list.map { it.toDomain() } }

    override suspend fun insert(set: CardioSet): Long =
        dao.insert(set.toEntity())

    override suspend fun insertAll(list: List<CardioSet>) =
        dao.insertAll(list.map { it.toEntity() })

    override suspend fun delete(set: CardioSet) =
        dao.delete(set.toEntity())
}

//Mappers
fun CardioSetEntity.toDomain() = CardioSet(id, exerciseId, routineId, minutes, order)
fun CardioSet.toEntity() = CardioSetEntity(id, exerciseId, routineId, minutes, order)