package com.liudao.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import java.time.LocalDate

@Entity(
    tableName = "periods",
    foreignKeys = [
        ForeignKey(
            entity = SupplementEntity::class,
            parentColumns = ["id"],
            childColumns = ["suppId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["suppId"])]
)
data class PeriodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val suppId: Long,
    val start: LocalDate,
    val end: LocalDate? = null
)
