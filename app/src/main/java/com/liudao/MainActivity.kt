package com.liudao

import com.liudao.navigation.NavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.liudao.ui.theme.LiuDaoTheme
import com.liudao.components.BottomNavBar
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.liudao.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiuDaoStyledApp()
        }
    }
}

@Composable
fun LiuDaoStyledApp() {
    val navController = rememberNavController()
    LiuDaoTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController) },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(BackgroundL, BackgroundR)
                        )
                    )
            ) {
                NavGraph(navController)
            }
        }
    }

}