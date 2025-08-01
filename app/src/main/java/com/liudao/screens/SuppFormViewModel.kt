package com.liudao.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liudao.domain.models.Supplement
import com.liudao.domain.models.Period
import com.liudao.domain.repositories.SupplementRepository
import com.liudao.domain.repositories.PeriodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class SuppFormUiState(
    val name: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val periods: List<Period> = emptyList(),
    val isEdit: Boolean = false,
    val supplementId: Long? = null,
    val errorMessage: String? = null,
    val canAddPeriod: Boolean = false
)

@HiltViewModel
class SuppFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val suppRepo: SupplementRepository,
    private val periodRepo: PeriodRepository
): ViewModel(){
    private val suppId: Long? = savedStateHandle["id"]
    private val _uiState = MutableStateFlow(SuppFormUiState())
    val uiState: StateFlow<SuppFormUiState> = _uiState.asStateFlow()

    init {
        if (suppId != null) {
            viewModelScope.launch {
                val supplement = suppRepo.getById(suppId)
                val periods = periodRepo.getPeriodsBySupp(suppId)
                supplement?.let {
                    _uiState.update { state ->
                        state.copy(
                            name = it.name,
                            periods = periods.first(),
                            isEdit = true,
                            supplementId = it.id,
                            canAddPeriod = AddAllowedPeriod(periods.first())
                        )
                    }
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onStartDateChange(date: LocalDate) {
        _uiState.update { it.copy(startDate = date) }
    }

    fun onEndDateChange(date: LocalDate?) {
        _uiState.update { it.copy(endDate = date) }
    }

    fun onAddPeriod() {
        viewModelScope.launch {
            val id = _uiState.value.supplementId ?: return@launch
            val start = _uiState.value.startDate
            val end = _uiState.value.endDate

            // Validaciones mínimas
            if (start == null || start > LocalDate.now()) return@launch
            if (end != null && end > LocalDate.now()) return@launch

            // Evitar solapamiento
            val existing = periodRepo.getPeriodsBySupp(id).first()
            val overlaps = existing.any { existingPeriod ->
                val existingStart = existingPeriod.start
                val existingEnd = existingPeriod.end ?: LocalDate.MAX

                val newStart = start
                val newEnd = end ?: LocalDate.MAX

                newStart <= existingEnd && newEnd >= existingStart
            }

            if (overlaps) return@launch // no lo agregamos

            // Insertar periodo
            val period = Period(
                suppId = id,
                start = start,
                end = end
            )
            periodRepo.insert(period)

            // Refrescar periodos y restricciones
            val updated = periodRepo.getPeriodsBySupp(id).first()
            _uiState.update {
                it.copy(
                    periods = updated,
                    startDate = null,
                    endDate = null,
                    canAddPeriod = AddAllowedPeriod(updated)
                )
            }
        }
    }


    fun onSave(onFinish: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val name = state.name.trim()
            val start = state.startDate
            val end = state.endDate

            if (name.isBlank() || start == null || start.isAfter(LocalDate.now()) || (end != null && end.isAfter(LocalDate.now()))) {
                _uiState.update { it.copy(errorMessage = "Fechas inválidas o nombre vacío") }
                return@launch
            }

            if (state.isEdit) {
                // Agregar nuevo periodo, si el último tiene fecha de fin y hay < 4 periodos
                if (state.periods.size >= 4) {
                    _uiState.update { it.copy(errorMessage = "Máximo 4 periodos permitidos") }
                    return@launch
                }
                if (state.periods.lastOrNull()?.end == null) {
                    _uiState.update { it.copy(errorMessage = "Cierra el último periodo antes de agregar uno nuevo") }
                    return@launch
                }

                val overlapping = state.periods.any {
                    val existingStart = it.start
                    val existingEnd = it.end ?: LocalDate.MAX
                    val newEnd = end ?: LocalDate.MAX
                    (start <= existingEnd && newEnd >= existingStart)
                }
                if (overlapping) {
                    _uiState.update { it.copy(errorMessage = "Las fechas se superponen con un periodo existente") }
                    return@launch
                }

                val updatedSupp = Supplement(id = state.supplementId ?: return@launch, name = name)
                suppRepo.update(updatedSupp)
                periodRepo.insert(Period(suppId = updatedSupp.id, start = start, end = end))

            } else {
                val newSupp = Supplement(name = name)
                val suppId = suppRepo.insert(newSupp)
                periodRepo.insert(Period(suppId = suppId, start = start, end = end))
            }
            onFinish()
        }
    }
}

fun AddAllowedPeriod(periods: List<Period>): Boolean{
    if (periods.size >= 4) {
        return false
    }
    if (periods.lastOrNull()?.end == null) {
        return false
    }
    return true
}