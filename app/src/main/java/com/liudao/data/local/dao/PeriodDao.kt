package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.liudao.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodDao {
    @Query("SELECT * FROM periods")
    fun getAll(): Flow<List<PeriodEntity>>

    @Insert
    suspend fun insert(period: PeriodEntity): Long

    @Insert
    suspend fun insertAll(list: List<PeriodEntity>)

    @Query("SELECT * FROM periods WHERE suppId = :supplement ORDER BY start DESC")
    fun getPeriodsBySupp(supplement: Long): Flow<List<PeriodEntity>>

    @Delete
    suspend fun delete(period: PeriodEntity)

    @Update
    suspend fun update(period: PeriodEntity)

}