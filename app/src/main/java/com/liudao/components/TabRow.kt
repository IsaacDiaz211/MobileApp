package com.liudao.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TabRow(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    androidx.compose.material3.TabRow(
        selectedTabIndex = if (selectedTab == "Ejercicios") 0 else 1,
        containerColor = Color.Transparent
    ) {
        Tab(
            selected = selectedTab == "Ejercicios",
            onClick = { onTabSelected("Ejercicios") },
            text = { Text("Ejercicios") }
        )
        Tab(
            selected = selectedTab == "Suplementos",
            onClick = { onTabSelected("Suplementos") },
            text = { Text("Suplementos") }
        )
    }
}