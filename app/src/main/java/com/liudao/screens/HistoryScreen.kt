package com.liudao.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.liudao.ui.theme.LiuDaoTheme
import com.liudao.ui.theme.OnPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    LiuDaoTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Historial",
                            color = OnPrimary,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        )
        { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                /*Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(40.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Buscar Ejercicio", color = Color.White, modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }*/

                Spacer(Modifier.height(62.dp))
                Text("Acá irá el historial", color = Color.White)
            }
        }

    }

}