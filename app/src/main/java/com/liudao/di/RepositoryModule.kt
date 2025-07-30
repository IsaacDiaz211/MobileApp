package com.liudao.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.liudao.domain.repositories.ExerciseRepository
import com.liudao.domain.interfaces.IExerciseRepository
import com.liudao.domain.interfaces.IMuscleGroupRepository
import com.liudao.domain.interfaces.IRoutineRepository
import com.liudao.domain.interfaces.ISetRepository
import com.liudao.domain.interfaces.ISupplementRepository
import com.liudao.domain.interfaces.ICardioSetRepository
import com.liudao.domain.repositories.MuscleGroupRepository
import com.liudao.domain.repositories.RoutineRepository
import com.liudao.domain.repositories.SetRepository
import com.liudao.domain.repositories.SupplementRepository
import com.liudao.domain.repositories.CardioSetRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExerciseRepository(
        exerciseRepoImpl: ExerciseRepository
    ): IExerciseRepository

    @Binds
    abstract fun bindMuscleGroupRepository(
        muscleGroupRepoImpl: MuscleGroupRepository
    ): IMuscleGroupRepository

    @Binds
    abstract fun bindRoutineRepository(
        routineRepoImpl: RoutineRepository
    ): IRoutineRepository

    @Binds
    abstract fun bindSetRepository(
        setRepoImpl: SetRepository
    ): ISetRepository

    @Binds
    abstract fun bindCardioSetRepository(
        cardioRepoImpl: CardioSetRepository
    ): ICardioSetRepository

    @Binds
    abstract fun bindSupplementRepository(
        supRepoImpl: SupplementRepository
    ): ISupplementRepository

    @Binds
    abstract fun bindPeriodRepository(
        periodRepoImpl: PeriodRepository
    ): IPeriodRepository
}