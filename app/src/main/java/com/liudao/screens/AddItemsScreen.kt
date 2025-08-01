package com.liudao.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.Supplement
import com.liudao.components.ExpandableFab
import com.liudao.components.TopTabSelector

@Composable
fun AddItemsScreen(
    nc: NavController,
    vm: AddItemsViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    var fabExpanded by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopTabSelector(
                selectedTab = state.selectedTab,
                onTabSelected = vm::onTabSelected
            )
        },
        floatingActionButton = {
            ExpandableFab(
                expanded = fabExpanded,
                onFabClick = { fabExpanded = !fabExpanded },
                onNewExerciseClick = { nc.navigate("itemForm/Ejercicio") },
                onNewSupplementClick = { nc.navigate("itemForm/Suplemento") },
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
        ) {
            when (state.selectedTab) {
                "Ejercicios" -> ExerciseList(
                    exercises = state.exercises,
                    onEdit = { exercise -> nc.navigate("itemForm/Ejercicio?id=${exercise.id}") },
                    onDelete = vm::onDeleteExercise
                )
                "Suplementos" -> SupplementList(
                    supplements = state.supplements,
                    onEdit = { supplement -> nc.navigate("itemForm/Suplemento?id=${supplement.id}") },
                    onDelete = vm::onDeleteSupplement
                )
            }
        }
    }
}


@Composable
fun ExerciseList(
    exercises: List<Exercise>,
    onEdit: (Exercise) -> Unit,
    onDelete: (Long) -> Unit
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
    onDelete: (Long) -> Unit
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
    onDelete: (Long) -> Unit
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
        IconButton(onClick = { onDelete(exercise.id) }) {
            Icon(Icons.Default.Delete,
                "Eliminar",
                tint = Color.White)
        }
    }
}

@Composable
fun SupplementItem(
    supp: Supplement,
    onEdit: (Supplement) -> Unit,
    onDelete: (Long) -> Unit
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
        IconButton(onClick = { onDelete(supp.id) }) {
            Icon(Icons.Default.Delete, "Eliminar")
        }
    }
}