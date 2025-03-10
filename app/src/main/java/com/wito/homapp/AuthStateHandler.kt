package com.wito.homapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AuthStateHandler(navController: NavHostController) {
    val authState = Firebase.auth.currentUser != null
    if (authState) {
        MainScreen(navController) // Usuario autentificado
    } else {
        LoginScreen(navController) // Usuario no autentificado
    }
}