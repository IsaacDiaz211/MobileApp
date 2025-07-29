package com.liudao.data.local.preload

import com.liudao.data.local.dao.MuscleGroupDao
import com.liudao.data.local.entities.MuscleGroupEntity
import javax.inject.Inject

class Preloader @Inject constructor(
    private val dao: MuscleGroupDao
) {
    suspend operator fun invoke() {
        if (dao.count() == 0) {
            listOf(
                "Pecho", "Espalda", "Piernas", "Tríceps", "Bíceps",
                "Hombros", "Core", "Antebrazos", "Cardio"
            ).forEach { name ->
                dao.insert(MuscleGroupEntity(name = name))
            }
        }
    }
}