package com.liudao.domain.repositories

import com.liudao.data.local.dao.PeriodDao
import com.liudao.data.local.entities.PeriodEntity
import com.liudao.domain.interfaces.IPeriodRepository
import com.liudao.domain.models.Period
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PeriodRepository @Inject constructor(
    private val dao: PeriodDao
): IPeriodRepository {
    override fun getAll(): Flow<List<Period>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun insert(period: Period): Long =
        dao.insert(period.toEntity())

    override suspend fun delete(period: Period) =
        dao.delete(period.toEntity())

    override fun getPeriodsBySupp(supplement: Long): Flow<List<Period>> =
        dao.getPeriodsBySupp(supplement).map { list -> list.map { it.toDomain() } }

    override suspend fun update(period: Period) =
        dao.update(period.toEntity())
}

//Mappers
fun PeriodEntity.toDomain() = Period(id, suppId, start, end)
fun Period.toEntity() = PeriodEntity(id, suppId, start, end)