package com.liudao.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview
import com.liudao.domain.models.Exercise

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopBar(date = state.today) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1) Buscador
            SearchRow(
                text = state.searchText,
                onTextChange = vm::onSearchChange,
                onAddClick = { /* abre diálogo crear ejercicio */ }
            )

            // 2) Lista desplegable de coincidencias
            if (state.searchResults.isNotEmpty()) {
                ExerciseSuggestions(
                    list = state.searchResults,
                    onSelect = { ex ->
                        vm.selectExercise(ex)
                        // Mostrar inmediatamente los inputs para ese ejercicio
                    }
                )
            }

            // 3) Sets actuales (agrupados por ejercicio)
            SetsList(
                sets = state.currentSets,
                onAddSameWeight = { idx -> /* igual peso */ },
                onAddDifferentWeight = { idx -> /* nuevo peso */ }
            )
        }
    }
}

@Composable
fun TopBar(date: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}

@Composable
fun SearchRow(
    text: String,
    onTextChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text("Añadir ejercicio") },
            shape = RoundedCornerShape(50),
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onAddClick,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Nuevo")
        }
    }
}

@Composable
fun ExerciseSuggestions(
    list: List<Exercise>,
    onSelect: (Exercise) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(list) { ex ->
            TextButton(onClick = { onSelect(ex) }) {
                Text(ex.name)
            }
        }
    }
}

@Composable
fun SetsList(
    sets: List<PendingSet>,
    onAddSameWeight: (Int) -> Unit,
    onAddDifferentWeight: (Int) -> Unit
) {
    val grouped = sets.groupBy { it.exerciseName }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        grouped.forEach { (name, list) ->
            Text(name, style = MaterialTheme.typography.titleMedium)
            list.forEachIndexed { index, set ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${set.weight} kg x ${set.reps}")
                    IconButton(onClick = { onAddSameWeight(index) }) {
                        Icon(Icons.Default.Add, "misma carga")
                    }
                }
                IconButton(onClick = { onAddDifferentWeight(index) }) {
                    Icon(Icons.Default.Add, "carga distinta")
                }
            }
        }
    }
}