package com.liudao.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.liudao.screens.*


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Timer : Screen("timer")
    object Progress : Screen("progress")
    object History : Screen("history")
    object AddItems : Screen("addItems")

    object ItemForm : Screen("itemForm/{tipo}?id={id}") {
        fun route(tipo: String, id: Long? = null): String {
            return if (id != null) "ItemForm/$tipo?id=$id"
            else "ItemForm/$tipo"
        }
    }
}


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Timer.route) { TimerScreen() }
        composable(Screen.Progress.route) { ProgressScreen() }
        composable(Screen.History.route) { HistoryScreen() }
        composable(Screen.AddItems.route) { AddItemsScreen(navController) }
        composable(
            route = "itemForm/{tipo}?id={id}",
            arguments = listOf(
                navArgument("tipo") { type = NavType.StringType },
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: "Ejercicio"
            val id = backStackEntry.arguments?.getLong("id")?.takeIf { it != -1L }
            ItemFormScreen(navController)
        }
    }
}