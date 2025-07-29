package com.liudao.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.liudao.data.local.entities.*
import com.liudao.data.local.dao.*

@Database(
    entities = [
        SupplementEntity::class,
        RoutineEntity::class,
        MuscleGroupEntity::class,
        ExerciseEntity::class,
        SetEntity::class,
               CardioSetEntity::class],
    version = 3,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class LiuDaoDatabase: RoomDatabase() {
    abstract fun supplementDao(): SupplementDao
    abstract fun routineDao(): RoutineDao
    abstract fun muscleGroupDao(): MuscleGroupDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setDao(): SetDao
    abstract fun cardioSetDao(): CardioSetDao
}