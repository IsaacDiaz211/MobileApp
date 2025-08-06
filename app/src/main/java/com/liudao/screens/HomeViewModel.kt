package com.liudao.screens

import com.liudao.domain.models.Exercise
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liudao.domain.models.Routine
import com.liudao.domain.models.Set
import com.liudao.domain.repositories.ExerciseRepository
import com.liudao.domain.repositories.RoutineRepository
import com.liudao.domain.repositories.SetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

data class PendingSet(
    val exerciseId: Long,
    val exerciseName: String,
    val weight: Float,
    val reps: Int
)

data class HomeUiState(
    /*val today: String = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es"))), esta parte está deprecated*/
    val today: String = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("d 'de' MMMM", Locale.forLanguageTag("es-ES"))),

    val searchText: String = "",
    val searchResults: List<Exercise> = emptyList(),

    val currentSets: List<PendingSet> = emptyList(),
    val isCreatingNewExercise: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routineRepo: RoutineRepository,
    private val setRepo: SetRepository,
    private val exerciseRepo: ExerciseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onSearchChange(text: String) {
        _uiState.update { it.copy(searchText = text) }
        viewModelScope.launch {
            exerciseRepo.search(text).collect { list ->
                _uiState.update { state -> state.copy(searchResults = list) }
            }
        }
    }

    fun selectExercise(exercise: Exercise) {
        _uiState.update { it.copy(searchText = "", searchResults = emptyList()) }
        // se guarda internamente para mostrar los inputs
    }


    fun addSet(exercise: Exercise, weight: Float, reps: Int) {
        _uiState.update { state ->
            state.copy(
                currentSets = state.currentSets + PendingSet(
                    exerciseId = exercise.id,
                    exerciseName = exercise.name,
                    weight = weight,
                    reps = reps
                )
            )
        }
    }

    fun saveRoutine() = viewModelScope.launch {
        val routineId = routineRepo.insert(
            Routine(date = LocalDate.now())
        )
        val sets = _uiState.value.currentSets.mapIndexed { idx, set ->
            Set(
                exerciseId = set.exerciseId,
                routineId = routineId,
                weight = set.weight,
                reps = set.reps,
                order = idx + 1
            )
        }
        setRepo.insertAll(sets)
        _uiState.update { HomeUiState() }
    }
}