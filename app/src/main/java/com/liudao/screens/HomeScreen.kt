package com.liudao.screens

import com.liudao.ui.theme.LiuDaoTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.liudao.domain.models.Exercise
import com.liudao.ui.theme.OnPrimary


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Inicio",
                        color = OnPrimary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                var date = state.today
                Text(
                    "Rutina del $date",
                    style = MaterialTheme.typography.headlineSmall,
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
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.Transparent)
                    .padding(horizontal = 16.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                // 1) Buscador
                SearchRow(
                    modifier = Modifier
                        .fillMaxSize(),
                    text = state.searchText,
                    onTextChange = vm::onSearchChange,
                    onAddClick = { /* acción para crear ejercicio */ }
                )
            }


        }
    }
}

@Composable
fun SearchRow(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier,
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
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape
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
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (list.isEmpty()) {
            // TODO Mostrar un mensaje si no hay coincidencias
            // Text("No hay coincidencias.")
        } else {
            list.forEach { ex ->
                TextButton(
                    onClick = { onSelect(ex) },
                    modifier = Modifier.fillMaxWidth() // Para que el TextButton ocupe el ancho
                ) {
                    Text(ex.name)
                }
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