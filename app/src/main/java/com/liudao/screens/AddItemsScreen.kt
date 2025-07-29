package com.liudao.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.liudao.components.TabRow
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liudao.domain.models.Exercise
import com.liudao.domain.models.Supplement
import com.liudao.components.ExpandableFab

@Composable
fun AddItemsScreen(
    vm: AddItemsViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    var fabExpanded by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = { TabRow(
            selectedTab = state.selectedTab,
            onTabSelected = vm::onTabSelected
        ) },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (state.selectedTab) {
                "Ejercicios" -> ExerciseList(state.exercises)
                "Suplementos" -> SupplementList(state.supplements)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                ExpandableFab(
                    expanded = fabExpanded,
                    onFabClick = { fabExpanded = !fabExpanded },
                    onNewExerciseClick = {
                        fabExpanded = false
                        vm.addNewItem() // o navegación
                    },
                    onNewSupplementClick = {
                        fabExpanded = false
                        vm.addNewItem() // o navegación
                    }
                )
            }
        }

    }
}

@Composable
fun ExerciseList(
    exercises: List<Exercise>
) {
}

@Composable
fun SupplementList(
    supplements: List<Supplement>
) {
}