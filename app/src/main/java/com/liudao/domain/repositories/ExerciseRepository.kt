package com.liudao.domain.repositories

import com.liudao.data.local.dao.ExerciseDao
import com.liudao.data.local.entities.ExerciseEntity
import com.liudao.domain.interfaces.IExerciseRepository
import com.liudao.domain.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val dao: ExerciseDao
) : IExerciseRepository {

    override fun getAll(): Flow<List<Exercise>> =
        dao.getExercises().map { list -> list.map { it.toDomain() } }

    override suspend fun insert(exercise: Exercise): Long =
        dao.insert(exercise.toEntity())

    override suspend fun delete(exercise: Long) {
        var toDelete = dao.getById(exercise)
        if(toDelete != null) {
            dao.delete(toDelete)
        }
    }

    override suspend fun search(text: String): Flow<List<Exercise>> =
        dao.search(text).map { list -> list.map { it.toDomain() } }

    override suspend fun update(exercise: Exercise) {
        dao.update(exercise.toEntity())
    }
    override suspend fun getById(id: Long): Exercise? = withContext(Dispatchers.IO) {
        dao.getById(id)?.toDomain()
    }

}

//Mappers
fun ExerciseEntity.toDomain() = Exercise(id, name, idMuscleGroup)
fun Exercise.toEntity() = ExerciseEntity(id = this.id ?: 0L, name, muscleGroupId)