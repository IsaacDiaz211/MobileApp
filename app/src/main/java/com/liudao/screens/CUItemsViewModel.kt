package com.liudao.screens

import androidx.lifecycle.ViewModel
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.Supplement
import com.liudao.domain.repositories.ExerciseRepository
import com.liudao.domain.repositories.PeriodRepository
import com.liudao.domain.repositories.SupplementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class CUAddItemsUiState(
    val selectedTab: String = "Ejercicios",
    val exercises: List<Exercise> = emptyList(),
    val supplements: List<Supplement> = emptyList()
)

@HiltViewModel
class CUItemsViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepository,
    private val supplementRepo: SupplementRepository,
    private val periodRepo: PeriodRepository
): ViewModel()  {
}