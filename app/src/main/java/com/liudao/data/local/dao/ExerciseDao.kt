package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.liudao.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE idMuscleGroup = :group")
    fun getForGroup(group: Int): Flow<List<ExerciseEntity>>

    @Insert
    suspend fun insert(exercise: ExerciseEntity): Long

    @Delete
    suspend fun delete(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :text || '%'")
    fun search(text: String): Flow<List<ExerciseEntity>>

    @Update
    suspend fun update(exercise: ExerciseEntity)
}