package com.liudao.screens

import com.liudao.ui.theme.LiuDaoTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip

@Composable
fun ProgressScreen() {

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Row(
                Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White.copy(alpha = 0.15f))
            ) {
                listOf("Ejercicios", "Suplementos").forEachIndexed { index, label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(if (index == 0) Color.White.copy(alpha = 0.25f) else Color.Transparent)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(label, color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
            // Acá irán los gráficos
            Text("Acá irán los gráficos", color = Color.White)
        }

}