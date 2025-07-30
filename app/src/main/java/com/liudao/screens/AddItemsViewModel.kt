package com.liudao.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.Supplement
import com.liudao.domain.models.Period
import com.liudao.domain.repositories.ExerciseRepository
import com.liudao.domain.repositories.SupplementRepository
import com.liudao.domain.repositories.PeriodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AddItemsUiState(
    val selectedTab: String = "Ejercicios",
    val exercises: List<Exercise> = emptyList(),
    val supplements: List<Supplement> = emptyList()
)


@HiltViewModel
class AddItemsViewModel @Inject constructor(
    private val exerciseRepo: ExerciseRepository,
    private val supplementRepo: SupplementRepository
) : ViewModel() {
    private val selectedTab = MutableStateFlow("Ejercicios") // Tab actual

    val uiState: StateFlow<AddItemsUiState> = combine(
        exerciseRepo.getAll(),                   // Flow<List<Exercise>>
        supplementRepo.getAll(),                 // Flow<List<Supplement>>
        selectedTab                              // Flow<String>
    ) { exercises, supplements, tab ->
        AddItemsUiState(
            selectedTab = tab,
            exercises = exercises,
            supplements = supplements
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddItemsUiState()
    )

    fun onTabSelected(tab: String) {
        selectedTab.value = tab
    }



    // TODO: implementar la eliminación con una ventana de advertencia
    fun onDeleteExercise(id: Long) {
        viewModelScope.launch {
            //exerciseRepo.deleteById(id)
        }
    }

    fun onDeleteSupplement(id: Long) {
        viewModelScope.launch {
            //supplementRepo.deleteById(id)
        }
    }
}