package com.liudao.screens

import com.liudao.ui.theme.LiuDaoTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip

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
fun TimerScreen() {
    LiuDaoTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Temporizador 00:00
            Text(
                text = "00:00",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botones de tiempo predefinido
            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listOf("1:30", "2:30", "3:00", "5:00").forEach { time ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E1E1E))
                            .clickable { /* Iniciar tiempo */ }
                            .padding(16.dp)
                    ) {
                        Text(text = time, color = Color.White)
                    }
                }
            }

            // Botón "Iniciar" (óvalo alargado)
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF4A6CF7))
                    .clickable { /* Lógica */ },
                contentAlignment = Alignment.Center
            ) {
                Text("Iniciar", color = Color.White)
            }
        }
    }

}