package com.wito.homapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavHostController){
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Perfil de Usuario")

        Button(
            onClick = {
                signOut(auth, navController)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}

fun signOut(auth: FirebaseAuth, navController: NavHostController){
    auth.signOut()
    navController.navigate("login") {
        popUpTo("profile") {inclusive = true}
    }
}