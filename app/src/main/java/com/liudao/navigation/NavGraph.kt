package com.liudao.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.liudao.screens.*


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Timer : Screen("timer")
    object Progress : Screen("progress")
    object History : Screen("history")
    object AddItems : Screen("addItems")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Timer.route) { TimerScreen() }
        composable(Screen.Progress.route) { ProgressScreen() }
        composable(Screen.History.route) { HistoryScreen() }
        composable(Screen.AddItems.route) { AddItemsScreen() }
    }
}