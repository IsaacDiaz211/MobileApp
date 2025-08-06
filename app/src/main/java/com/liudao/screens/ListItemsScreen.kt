package com.liudao.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.Supplement
import com.liudao.components.ExpandableFab
import com.liudao.components.LDAlertDialog
import com.liudao.components.LDButton
import com.liudao.components.SubTab
import com.liudao.ui.theme.Container
import com.liudao.ui.theme.Error
import com.liudao.ui.theme.LiuDaoTheme

@Composable
fun ListItemsScreen(
    nc: NavController,
    vm: ListItemsViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    var fabExpanded by rememberSaveable { mutableStateOf(false) }
    var exerciseToDelete by rememberSaveable { mutableStateOf<Exercise?>(null) }
    var supplementToDelete by rememberSaveable { mutableStateOf<Supplement?>(null) }
    val scrimVisible = fabExpanded

    val fabHeightDp = 72.dp

    // Este estado controla la visibilidad del FAB de forma directa con nestedScroll
    var fabVisibleNestedScroll by rememberSaveable { mutableStateOf(true) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Si el scroll es hacia abajo y el FAB está visible, se oculta
                if (available.y < -5 && fabVisibleNestedScroll) {
                    fabVisibleNestedScroll = false
                    return Offset.Zero
                }
                // Si el scroll es hacia arriba y el FAB no está visible, se muestra
                if (available.y > 5 && !fabVisibleNestedScroll) {
                    fabVisibleNestedScroll = true
                    return Offset.Zero
                }
                return Offset.Zero
            }
        }
    }
        Scaffold(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            topBar = {
                SubTab(
                    selectedTab = state.selectedTab,
                    onTabSelected = vm::onTabSelected
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = fabVisibleNestedScroll,
                    enter = slideInVertically(initialOffsetY = { it * 2 }),
                    exit = slideOutVertically(targetOffsetY = { it * 2 })
                ){
                    ExpandableFab(
                        expanded = fabExpanded,
                        onFabClick = {
                            fabExpanded = !fabExpanded
                            if (fabExpanded && !fabVisibleNestedScroll) {
                                // Si lo expandimos y estaba oculto por scroll, lo forzamos a ser visible
                                fabVisibleNestedScroll = true
                            }
                        },
                        onNewExerciseClick = {
                            fabExpanded = false
                            nc.navigate("exerciseForm/")
                        },
                        onNewSupplementClick = {
                            fabExpanded = false
                            nc.navigate("suppForm/")
                        },
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                LaunchedEffect(state.selectedTab) {
                    if (fabExpanded) {
                        fabExpanded = false
                    }
                }
                when (state.selectedTab) {
                    "Ejercicios" -> {
                        ExerciseList(
                        exercises = state.exercises,
                        onEdit = { exercise -> nc.navigate("exerciseForm/?id=${exercise.id}") },
                        onDelete = { exerciseToDelete = it },
                        )
                    }

                    "Suplementos" -> {
                        SupplementList(
                            supplements = state.supplements,
                            onEdit = { supplement -> nc.navigate("suppForm/?id=${supplement.id}") },
                            onDelete = { supplementToDelete = it }
                        )
                    }
                }
                exerciseToDelete?.let { exercise ->
                    LDAlertDialog(
                        onDismissRequest = { exerciseToDelete = null },
                        title = { Text("Confirmar eliminación") },
                        text = { Text("¿Seguro que querés eliminar \"${exercise.name}\"?") },
                        confirmButton = {
                            LDButton(
                                text = "Eliminar",
                                isPrimary = true,
                                onClick = {
                                    vm.onDeleteExercise(exercise.id)
                                    exerciseToDelete = null
                                },
                                color = Error
                            )
                        },
                        dismissButton = {
                            LDButton(
                                text = "Cancelar",
                                onClick = { exerciseToDelete = null },
                                color = Color.Transparent
                            )
                        }
                    )
                }

                supplementToDelete?.let { supp ->
                    LDAlertDialog(
                        onDismissRequest = { exerciseToDelete = null },
                        title = { Text("Confirmar eliminación") },
                        text = { Text("¿Seguro que querés eliminar \"${supp.name}\"?") },
                        confirmButton = {
                            LDButton(
                                text = "Eliminar",
                                isPrimary = true,
                                onClick = {
                                    vm.onDeleteExercise(supp.id)
                                    supplementToDelete = null
                                },
                                color = Error
                            )
                        },
                        dismissButton = {
                            LDButton(
                                text = "Cancelar",
                                onClick = { supplementToDelete = null },
                                color = Container
                            )
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = scrimVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // Sin ripple al clickear el scrim
                            onClick = { fabExpanded = false } // Cierra FAB al clickear el scrim
                        ),
                    color = Color.Black.copy(alpha = 0.75f) // color y opacidad
                ) {}
            }
        }
}



@Composable
fun ExerciseList(
    exercises: List<Exercise>,
    onEdit: (Exercise) -> Unit,
    onDelete: (Exercise) -> Unit
) {
    Column {
        exercises.forEach {
            ExerciseItem(it, onEdit, onDelete)
        }
    }
}

@Composable
fun SupplementList(
    supplements: List<Supplement>,
    onEdit: (Supplement) -> Unit,
    onDelete: (Supplement) -> Unit
) {
    Column {
        supplements.forEach {
            SupplementItem(it, onEdit, onDelete)
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onEdit: (Exercise) -> Unit,
    onDelete: (Exercise) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit(exercise) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            exercise.name,
            Modifier.weight(1f),
            color = Color.White)
        IconButton(onClick = { onEdit(exercise) }) {
            Icon(Icons.Default.Edit,
                "Editar",
                tint = Color.White)
        }
        IconButton(onClick = { onDelete(exercise) }) {
            Icon(Icons.Default.Delete, "Eliminar")
        }
    }
}

@Composable
fun SupplementItem(
    supp: Supplement,
    onEdit: (Supplement) -> Unit,
    onDelete: (Supplement) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit(supp) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            supp.name,
            Modifier.weight(1f),
            color = Color.White)
        IconButton(onClick = { onEdit(supp) }) {
            Icon(Icons.Default.Edit, "Editar")
        }
        IconButton(onClick = { onDelete(supp) }) {
            Icon(Icons.Default.Delete, "Eliminar")
        }
    }
}