package com.liudao.di

import android.content.Context
import androidx.room.Room
import com.liudao.data.local.database.LiuDaoDatabase
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LiuDaoDatabase =
        Room.databaseBuilder(
            context,
            LiuDaoDatabase::class.java,
            "liudao_db"
        )
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideExerciseDao(db: LiuDaoDatabase) = db.exerciseDao()

    @Provides
    fun provideMuscleGroupDao(db: LiuDaoDatabase) = db.muscleGroupDao()

    @Provides
    fun provideRoutineDao(db: LiuDaoDatabase) = db.routineDao()

    @Provides
    fun provideSetDao(db: LiuDaoDatabase) = db.setDao()

    @Provides
    fun provideCardioSetDao(db: LiuDaoDatabase) = db.cardioSetDao()

    @Provides
    fun provideSupplementDao(db: LiuDaoDatabase) = db.supplementDao()

    @Provides
    fun providePeriodDao(db: LiuDaoDatabase) = db.periodDao()
}