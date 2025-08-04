package com.liudao.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.liudao.ui.theme.Brand
import com.liudao.ui.theme.OnPrimary

/*@Composable
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
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.FitnessCenter, contentDescription = "Nuevo Ejercicio")
            }

            FloatingActionButton(
                onClick = onNewSupplementClick,
                modifier = Modifier.alpha(alpha),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.LocalDrink, contentDescription = "Nuevo Suplemento")
            }
        }

        FloatingActionButton(
            onClick = onFabClick,
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}*/

@Composable
fun ExpandableFab(
    expanded: Boolean,
    onFabClick: () -> Unit,
    onNewExerciseClick: () -> Unit,
    onNewSupplementClick: () -> Unit
) {
    // Animaciones
    val transition = updateTransition(targetState = expanded, label = "fabTransition")

    val rotation by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 200) },
        label = "rotateIcon"
    ) { if (it) 45f else 0f }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 150) },
        label = "alphaChildren"
    ) { if (it) 1f else 0f }

    val elevation by transition.animateDp(
        transitionSpec = { tween(durationMillis = 200) },
        label = "elevation"
    ) { if (it) 8.dp else 6.dp }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (expanded) {
            ExtendedFabItem(
                visible = expanded,
                alpha = alpha,
                icon = Icons.Default.FitnessCenter,
                textItem = "Nuevo Ejercicio",
                onClickExp = onNewExerciseClick
            )
            ExtendedFabItem(
                visible = expanded,
                alpha = alpha,
                icon = Icons.Default.LocalDrink,
                textItem = "Nuevo Suplemento",
                onClickExp = onNewSupplementClick
            )
        }

        // FAB Principal
        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .size(56.dp)
                .shadow(elevation = elevation, shape = CircleShape),
            shape = CircleShape,
            containerColor = Brand,  // Color(0xFF6C2A7F)
            contentColor = OnPrimary  // Color(0xFFE6CDEC)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
private fun ExtendedFabItem(
    visible: Boolean,
    alpha: Float,
    icon: ImageVector,
    textItem: String,
    onClickExp: () -> Unit
) {
    if (visible) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .alpha(alpha) // Aplicar alpha al Row completo para que se desvanezcan juntos
                .padding(horizontal = 2.dp)
        ) {
            Text(
                text = textItem,
                color = OnPrimary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
            // FAB Circular solo con el Icono
            FloatingActionButton(
                onClick = onClickExp, // El click se maneja aquí, en el botón
                shape = CircleShape,
                containerColor = Brand,
                modifier = Modifier
                    .size(50.dp)
                    //.padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = textItem, // El contentDescription puede ser el texto
                    tint = OnPrimary
                )
            }
        }
    }
}
