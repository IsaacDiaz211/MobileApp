package com.liudao.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
//import kotlinx.coroutines.time.delay
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {
    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> = _timeLeft
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private var timerJob: Job? = null

    private val _currentSeconds = MutableStateFlow(0)   // 0:00 por defecto
    val currentSeconds: StateFlow<Int> = _currentSeconds

    // Límites
    private companion object {
        const val MIN_SECONDS = 0
        const val MAX_SECONDS = 600
    }

    // Función para establecer el tiempo
    fun setDuration(seconds: Int) {
        _timeLeft.value = seconds * 1000L
        _currentSeconds.value = seconds
    }

    // Función para cambiar el tiempo
    fun adjustTime(delta: Int) {
        val new = (_currentSeconds.value + delta).coerceIn(MIN_SECONDS, MAX_SECONDS)
        _currentSeconds.value = new
        setDuration(new)
    }

    // Convierte segundos a mm:ss
    fun format(seconds: Int): String =
        "${seconds / 60}:${(seconds % 60).toString().padStart(2, '0')}"

    fun start() {
        if (_isRunning.value) return
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value -= 1000
            }
            _isRunning.value = false
        }
    }

    fun stop() {
        timerJob?.cancel()
        _isRunning.value = false
    }

    fun reset() {
        stop()
        _timeLeft.value = 0L
    }

    fun format(millis: Long): String =
        "${millis / 1000 / 60}:${(millis / 1000 % 60).toString().padStart(2, '0')}"
}