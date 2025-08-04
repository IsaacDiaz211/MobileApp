package com.liudao.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.liudao.ui.theme.Container
import com.liudao.ui.theme.OnPrimary
import com.liudao.ui.theme.Brand

@Composable
fun SubTab(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val titles = listOf("Ejercicios", "Suplementos")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // esto añade espacio entre las pildoritas
    ) {
        titles.forEach { title ->
            val selected = title == selectedTab
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (selected) Brand
                        else Container
                    )
                    .clickable { onTabSelected(title) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    color = if (selected) OnPrimary else Brand
                )
            }
        }
    }
}