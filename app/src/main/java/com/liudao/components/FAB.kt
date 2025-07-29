package com.liudao.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FAB(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        content = content,
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary
    )
}