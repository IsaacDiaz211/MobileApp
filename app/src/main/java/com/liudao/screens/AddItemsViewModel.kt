package com.liudao.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.Supplement
import com.liudao.domain.repositories.ExerciseRepository
import com.liudao.domain.repositories.SupplementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AddItemsUiState(
    val selectedTab: String = "Ejercicios",
    val isAdding: Boolean = false,
    val name: String = "",
    val groupId: Long = 0,
    val startDate: LocalDate = LocalDate.now(),
    val exercises: List<Exercise> = emptyList(),
    val supplements: List<Supplement> = emptyList(),
    val showFabMenu: Boolean = false
)

@HiltViewModel
class AddItemsViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val supplementRepository: SupplementRepository
) : ViewModel() {

    val _uiState = MutableStateFlow(AddItemsUiState())
    val uiState: StateFlow<AddItemsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            exerciseRepository.getAll().collect { list ->
                _uiState.update { it.copy(exercises = list) }
            }
            supplementRepository.getAll().collect { list ->
                _uiState.update { it.copy(supplements = list) }
            }
        }
    }

    fun onTabSelected(tab: String) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onAddButtonClick() {
        _uiState.update { it.copy(isAdding = true) }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onMuscleGroupChange(groupId: Long) {
        _uiState.update { it.copy(groupId = groupId) }
    }

    fun onStartDateChange(date: LocalDate) {
        _uiState.update { it.copy(startDate = date) }
    }

    fun addNewItem() = viewModelScope.launch {
        when (_uiState.value.selectedTab) {
            "Ejercicios" -> {
                val exercise = Exercise(
                    name = _uiState.value.name,
                    muscleGroupId = _uiState.value.groupId
                )
                exerciseRepository.insert(exercise)
            }
            "Suplementos" -> {
                val supplement = Supplement(
                    name = _uiState.value.name,
                    start = _uiState.value.startDate
                )
                supplementRepository.insert(supplement)
            }
        }
        _uiState.update { AddItemsUiState() }
    }

    fun toggleFabMenu() {
        _uiState.update { it.copy(showFabMenu = !it.showFabMenu) }
    }
}