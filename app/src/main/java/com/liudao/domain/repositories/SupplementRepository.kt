package com.liudao.domain.repositories

import com.liudao.data.local.dao.SupplementDao
import com.liudao.data.local.entities.SupplementEntity
import com.liudao.domain.interfaces.ISupplementRepository
import com.liudao.domain.models.Supplement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupplementRepository @Inject constructor(
    private val dao: SupplementDao
): ISupplementRepository {
    override fun getAll(): Flow<List<Supplement>> =
        dao.getSupplements().map { list -> list.map { it.toDomain() } }

    override suspend fun insert(sup: Supplement): Long =
        dao.insert(sup.toEntity())

    override suspend fun delete(sup: Long) {
        val toDelete = dao.getById(sup)
        if (toDelete != null) {
            dao.delete(toDelete)
        }
    }

    override suspend fun update(sup: Supplement) =
        dao.update(sup.toEntity())

    override suspend fun getById(id: Long): Supplement? = withContext(Dispatchers.IO) {
        dao.getById(id)?.toDomain()
    }
}

//Mappers
fun SupplementEntity.toDomain() = Supplement(id, name)
fun Supplement.toEntity() = SupplementEntity(id, name)