package com.liudao.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableFab(
    expanded: Boolean,
    onFabClick: () -> Unit,
    onNewExerciseClick: () -> Unit,
    onNewSupplementClick: () -> Unit
) {
    val transition = updateTransition(targetState = expanded, label = "fabTransition")

    val rotation by transition.animateFloat(label = "rotateIcon") { state ->
        if (state) 45f else 0f
    }

    val alpha by transition.animateFloat(label = "alphaChildren") { state ->
        if (state) 1f else 0f
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (expanded) {
            FloatingActionButton(
                onClick = onNewExerciseClick,
                modifier = Modifier.alpha(alpha),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.FitnessCenter, contentDescription = "Nuevo Ejercicio")
            }

            FloatingActionButton(
                onClick = onNewSupplementClick,
                modifier = Modifier.alpha(alpha),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.LocalDrink, contentDescription = "Nuevo Suplemento")
            }
        }

        FloatingActionButton(
            onClick = onFabClick,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}