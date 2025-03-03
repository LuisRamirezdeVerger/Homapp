package com.wito.homapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wito.homapp.ui.RegisterScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(
            navController = TODO()
        ) }
        composable("register") { RegisterScreen() }
        composable("otra_pantalla") { OtraPantalla() }
    }
}

@Composable
fun OtraPantalla() {
    Text(text = "Esta es otra pantalla")
}
