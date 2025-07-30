package com.liudao.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CUItemsScreen(
    vm: CUItemsViewModel = hiltViewModel(),
    tipo: String,
    itemId: Long?
){}