package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.liudao.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleGroupDao {
    @Query("SELECT * FROM muscle_groups")
    fun getMuscleGroups(): Flow<List<MuscleGroupEntity>>

    @Insert
    suspend fun insert(muscleGroup: MuscleGroupEntity): Long

    @Delete
    suspend fun delete(muscleGroup: MuscleGroupEntity)

    @Query("SELECT COUNT(*) FROM muscle_groups")
    suspend fun count(): Int

}