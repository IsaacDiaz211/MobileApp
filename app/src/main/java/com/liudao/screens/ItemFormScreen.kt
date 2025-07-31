package com.liudao.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.liudao.constants.DefaultMuscleGroups

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemFormScreen(
    nc: NavController,
    vm: ItemFormViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()

    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.exerciseId == null) "Nuevo ejercicio" else "Editar ejercicio",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nc.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.onSave { nc.popBackStack() }
                },
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Save, contentDescription = "Guardar")
            }
        },
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = vm::onNameChange,
                label = { Text("Nombre del ejercicio") },
                modifier = Modifier.fillMaxWidth()
            )

            // --- EXPOSED DROPDOWN MENU ---
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                val selectedGroupName = DefaultMuscleGroups
                    .firstOrNull { it.id == state.selectedGroupId }?.name ?: ""

                OutlinedTextField(
                    readOnly = true,
                    value = selectedGroupName,
                    onValueChange = {},
                    label = { Text("Grupo muscular") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor() // ✅ FUNCIONA aunque esté deprecado (se elimina en Compose 1.7)
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    DefaultMuscleGroups.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group.name) },
                            onClick = {
                                vm.onGroupSelected(group.id)
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}