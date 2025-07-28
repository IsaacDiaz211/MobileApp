package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.liudao.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routines")
    fun getRoutines(): Flow<List<RoutineEntity>>

    @Insert
    suspend fun insert(routine: RoutineEntity): Long

    @Delete
    suspend fun delete(routine: RoutineEntity)
}