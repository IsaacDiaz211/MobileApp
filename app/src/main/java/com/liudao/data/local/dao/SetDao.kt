package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.liudao.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {
    @Query("SELECT * FROM sets")
    fun getAll(): Flow<List<SetEntity>>

    @Insert
    suspend fun insert(set: SetEntity): Long

    @Insert
    suspend fun insertAll(list: List<SetEntity>)

    @Query("SELECT * FROM sets WHERE routineId = :routine ORDER BY `order` DESC")
    fun getSetsByRoutine(routine: Int): Flow<List<SetEntity>>

    @Delete
    suspend fun delete(set: SetEntity)
}