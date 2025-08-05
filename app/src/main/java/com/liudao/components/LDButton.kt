package com.liudao.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.liudao.ui.theme.Brand
import com.liudao.ui.theme.OnPrimary

// Botón estilo HMOS (Píldora)
@Composable
fun LDButton(
    text: String,
    isPrimary: Boolean = false,
    onClick: () -> Unit,
    color: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .background(
                if (color != null) color else if(isPrimary && color === null) Brand else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isPrimary) Color.Transparent else Brand,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isPrimary) OnPrimary else Brand,
            style = MaterialTheme.typography.labelLarge
        )
    }
}