package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.liudao.data.local.entities.CardioSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardioSetDao {
    @Query("SELECT * FROM cardio_sets")
    fun getAll(): Flow<List<CardioSetEntity>>

    @Insert
    suspend fun insert(set: CardioSetEntity): Long

    @Insert
    suspend fun insertAll(list: List<CardioSetEntity>)

    @Query("SELECT * FROM cardio_sets WHERE routineId = :routine ORDER BY `order` DESC")
    fun getCardioSetsByRoutine(routine: Int): Flow<List<CardioSetEntity>>

    @Delete
    suspend fun delete(set: CardioSetEntity)
}