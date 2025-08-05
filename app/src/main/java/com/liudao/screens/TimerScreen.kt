package com.liudao.screens

import com.liudao.ui.theme.LiuDaoTheme
import androidx.compose.foundation.background
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

//@Composable
/*fun TimerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("00:00", style = MaterialTheme.typography.displayLarge, color = Color.White)

        Spacer(Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("1:30", "2:30", "3:00", "5:00").forEach {
                Text(
                    it,
                    color = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                        .padding(12.dp)
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = { TODO  },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(56.dp)
        ) {
            Text("Iniciar")
        }
    }
}*****/
@Composable
fun TimerScreen(
    vm: TimerViewModel = hiltViewModel()
) {
    val timeLeft by vm.timeLeft.collectAsStateWithLifecycle()
    val isRunning by vm.isRunning.collectAsStateWithLifecycle()
    val currentSeconds by vm.currentSeconds.collectAsStateWithLifecycle()

    val times = listOf(90, 150, 180, 300) // segundos para 1:30, 2:30, 3:00, 5:00 min

    LiuDaoTheme {
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
                IconButton(
                    onClick = { vm.adjustTime(-30) },
                    enabled = currentSeconds >= 30
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Restar 30 s",
                        tint = if (currentSeconds >= 30) ContainerSelected else Container
                    )
                }
                // Temporizador
                Text(
                    text = vm.format(timeLeft),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                // Botón “+30”
                IconButton(
                    onClick = { vm.adjustTime(30) },
                    enabled = currentSeconds <= 570
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Sumar 30 s",
                        tint = if (currentSeconds <= 570) ContainerSelected else Container
                    )
                }
            }

            // Botones de tiempo
            Row(Modifier.padding(bottom = 32.dp), Arrangement.spacedBy(16.dp)) {
                times.forEach { sec ->
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Container)
                            .clickable { vm.setDuration(sec) }
                            .padding(16.dp)
                    ) {
                        Text(
                            "${sec / 60}:${(sec % 60).toString().padStart(2, '0')}",
                            color = OnPrimary
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
                    .background(Color(0xFF4A6CF7))
                    .clickable { if (isRunning) vm.stop() else vm.start() },
                Alignment.Center
            ) {
                Text(if (isRunning) "Detener" else "Iniciar", color = Brand)
            }
        }
    }
}