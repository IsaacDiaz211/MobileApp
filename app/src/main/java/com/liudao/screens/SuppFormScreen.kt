package com.liudao.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import java.time.Instant
import java.time.ZoneId
import com.liudao.ui.theme.LiuDaoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuppFormScreen(
    nc: NavController,
    vm: SuppFormViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()
    LiuDaoTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (state?.supplementId == null) "Nuevo suplemento" else "Editar suplemento",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { nc.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        vm.onSave {
                            nc.popBackStack()
                        }
                    },
                    shape = CircleShape,
                    contentColor = MaterialTheme.colorScheme.primary
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
                // Nombre del suplemento
                OutlinedTextField(
                    value = state?.name ?: "",
                    onValueChange = vm::onNameChange,
                    label = { Text(
                        "Nombre del suplemento",
                        color = Color.White
                    ) },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )

                // Fecha de inicio
                DateField(
                    label = "Fecha de inicio",
                    date = state?.startDate,
                    onDateSelected = vm::onStartDateChange
                )

                // Fecha de fin
                DateField(
                    label = "Fecha de fin (opcional)",
                    date = state?.endDate,
                    onDateSelected = vm::onEndDateChange
                )
                Button(onClick = { Log.d("SuppFormScreen", "Test Button Clicked") }) {
                    Text("Test Click")
                }

                // Periodos existentes
                if (state?.isEdit == true && state!!.periods.isNotEmpty()) {
                    Text("Periodos previos:", style = MaterialTheme.typography.titleMedium)
                    state!!.periods.forEachIndexed { i, period ->
                        Text("- ${period.start} hasta ${period.end ?: "Actual"}")
                    }
                }

                // Agregar nuevo periodo
                if (state?.isEdit == true && state!!.canAddPeriod) {
                    Button(onClick = vm::onAddPeriod) {
                        Text("Agregar nuevo periodo")
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val dateStr = date?.format(formatter) ?: ""

    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
            ?: Instant.now().toEpochMilli()
    )

    Log.d("DateField", "DateField Composable: label='$label', showDialog=$showDialog")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.d("DateField", "Box clicked for label: $label")
                showDialog = true
            }
    ) {
        OutlinedTextField(
            value = dateStr,
            onValueChange = {},
            readOnly = true, // 🔒 Evita que el teclado aparezca
            label = { Text(label, color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false, // Opcional: desactiva el campo para evitar foco
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.White,
                disabledLabelColor = Color.White,
                disabledBorderColor = Color.White
            )
        )
    }

    if (showDialog) {
        Log.d("DateField", "Mostrando DatePickerDialog para $label")
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selected = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selected)
                    }
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}