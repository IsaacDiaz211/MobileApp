package com.liudao.data.local.preload

import android.util.Log
import com.liudao.data.local.dao.ExerciseDao
import com.liudao.data.local.dao.MuscleGroupDao
import com.liudao.data.local.entities.MuscleGroupEntity
import javax.inject.Inject
import com.liudao.data.local.entities.ExerciseEntity

class Preloader @Inject constructor(
    private val mgDao: MuscleGroupDao,
    private val exDao: ExerciseDao
) {
    suspend operator fun invoke() {
        var i: Long = 0
        if (mgDao.count() == 0) {
            val groups = listOf(
                "Pecho" to listOf("Press banca", "Press declinado"),
                "Espalda" to listOf("Dominadas", "Remo sentado"),
                "Piernas" to listOf("Sentadilla libre", "Extensiones de cuadríceps"),
                "Tríceps" to listOf("Extensiones con barra", "Extensiones unilateral"),
                "Bíceps" to listOf("Curl predicador", "Curl en banco inclinado"),
                "Hombros" to listOf("Press militar", "Elevaciones laterales"),
                "Core" to listOf("Plancha", "Crunches"),
                "Antebrazos" to listOf("Muñeca con mancuerna"),
                "Cardio" to listOf("Bicicleta estática", "Cinta")
            )
            groups.forEach { (groupName, exercises) ->
                i++
                val groupId = mgDao.insert(MuscleGroupEntity(id = i, name = groupName))
                Log.d("Preloader", "Inserted group: $groupName with id: $groupId")
                exercises.forEach { exName ->
                    exDao.insert(ExerciseEntity(name = exName, idMuscleGroup = groupId))
                }
            }
        }
    }
}