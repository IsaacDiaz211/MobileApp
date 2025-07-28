package com.liudao.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF50315E),
        tonalElevation = 4.dp
    ) {
        listOf(
            com.liudao.navigation.Screen.Home to Icons.Default.Home,
            com.liudao.navigation.Screen.Timer to Icons.Default.Timer,
            com.liudao.navigation.Screen.Progress to Icons.Default.BarChart,
            com.liudao.navigation.Screen.History to Icons.AutoMirrored.Filled.List,
            com.liudao.navigation.Screen.AddItems to Icons.Default.AddCircleOutline
        ).forEach { (screen, icon) ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(icon, contentDescription = null)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    indicatorColor = Color(0xFF780CAD)
                )
            )
        }
    }
}