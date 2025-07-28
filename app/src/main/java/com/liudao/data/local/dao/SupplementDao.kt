package com.liudao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.liudao.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplementDao {
    @Query("SELECT * FROM supplements")
    fun getSupplements(): Flow<List<SupplementEntity>>

    @Insert
    suspend fun insert(supplement: SupplementEntity): Long

    @Delete
    suspend fun delete(supplement: SupplementEntity)
}