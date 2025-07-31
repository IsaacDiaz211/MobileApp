package com.liudao.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ItemFormScreen(
    vm: ItemFormViewModel = hiltViewModel(),
    tipo: String,
    itemId: Long?
){}