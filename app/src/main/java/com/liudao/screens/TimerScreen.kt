package com.liudao.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liudao.ui.theme.Brand
import com.liudao.ui.theme.Container
import com.liudao.ui.theme.ContainerSelected
import com.liudao.ui.theme.OnPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    vm: TimerViewModel = hiltViewModel()
) {
    val timeLeft by vm.timeLeft.collectAsStateWithLifecycle()
    val isRunning by vm.isRunning.collectAsStateWithLifecycle()
    val currentSeconds by vm.currentSeconds.collectAsStateWithLifecycle()

    val times = listOf(90, 150, 180, 300) // segundos para 1:30, 2:30, 3:00, 5:00 min

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Temporizador",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    )
    { padding ->
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {

                // Botón “-30”
                Button(
                    onClick = { vm.adjustTime(-30) },
                    enabled = currentSeconds >= 30 && !isRunning,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentSeconds >= 30) ContainerSelected else Container
                    )
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Restar 30 s",
                        tint = OnPrimary
                    )
                }
                // Temporizador
                Text(
                    text = vm.format(timeLeft),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(bottom = 32.dp),
                    color = OnPrimary
                )
                // Botón “+30”
                Button(
                    onClick = { vm.adjustTime(30) },
                    enabled = currentSeconds <= 570,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentSeconds >= 30) ContainerSelected else Container
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Sumar 30 s",
                        tint = OnPrimary
                    )
                }
            }

            // Botones de tiempo
            Row(
                Modifier.padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    times.forEach { sec ->
                        val isSelected: Boolean = sec == currentSeconds
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .then(
                                    if(isSelected)
                                        Modifier.border(
                                            width = 2.dp,
                                            color = Brand,
                                            shape = CircleShape
                                        )
                                    else Modifier
                                )
                                .background(Container)
                                .clickable { vm.setDuration(sec) }
                                .padding(16.dp)
                        ) {
                            Text(
                                "${sec / 60}:${(sec % 60).toString().padStart(2, '0')}",
                                color = Brand
                            )
                        }
                    }
            }

            // Botón Iniciar / Detener
            Box(
                Modifier
                    .width(120.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brand)
                    .clickable { if (isRunning) vm.stop() else vm.start() },
                Alignment.Center
            ) {
                Text(if (isRunning) "Detener" else "Iniciar", color = OnPrimary)
            }
        }
    }
}