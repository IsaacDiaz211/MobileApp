package com.liudao.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.MuscleGroup
import com.liudao.domain.repositories.ExerciseRepository
import com.liudao.constants.DefaultMuscleGroups
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseFormUiState(
    val name: String = "",
    val selectedGroupId: Long? = null,
    val muscleGroups: List<MuscleGroup> = DefaultMuscleGroups,
    val isEdit: Boolean = false,
    val exerciseId: Long? = null,
)

@HiltViewModel
class ExerciseFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exerciseRepo: ExerciseRepository
): ViewModel()  {
    private val exerciseId: Long? = savedStateHandle["id"]
    private val _uiState = MutableStateFlow(ExerciseFormUiState())
    val uiState: StateFlow<ExerciseFormUiState> = _uiState.asStateFlow()

    init {
        if (exerciseId != null) {
            viewModelScope.launch {
                val exercise = exerciseRepo.getById(exerciseId)
                exercise?.let { ex ->
                    _uiState.update { state ->
                        state.copy(
                            name = ex.name,
                            selectedGroupId = ex.muscleGroupId,
                            isEdit = true,
                            exerciseId = ex.id
                        )
                    }
                }
            }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onGroupSelected(groupId: Long) {
        _uiState.update { it.copy(selectedGroupId = groupId) }
    }

    fun onSave(onFinish: () -> Unit) {
        viewModelScope.launch {
            val name = _uiState.value.name.trim()
            val groupId = _uiState.value.selectedGroupId

            if (name.isBlank() || groupId == null) return@launch

            if (_uiState.value.isEdit) {
                val updated = Exercise(
                    id = _uiState.value.exerciseId ?: return@launch,
                    name = name,
                    muscleGroupId = groupId
                )
                exerciseRepo.update(updated)
            } else {
                val new = Exercise(name = name, muscleGroupId = groupId)
                var id = exerciseRepo.insert(new)
            }
            onFinish()
        }
    }
}
