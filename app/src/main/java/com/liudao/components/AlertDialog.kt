package com.liudao.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.liudao.ui.theme.*

@Composable
fun LDAlertDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = ContainerSelected.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Brand.copy(alpha = 0.3f), // Borde sutil
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.titleLarge) {
                    title()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Texto
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
                    text()
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botones (en fila)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        dismissButton()
                        Spacer(modifier = Modifier.width(8.dp))
                        confirmButton()
                    }
                )
            }
        }
    }
}