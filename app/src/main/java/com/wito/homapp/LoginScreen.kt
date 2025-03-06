package com.wito.homapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen (navController: NavController){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text ("Correo electrónico")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button( onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()){
                signInWithEmailAndPassword(email, password, navController) {
                    error -> errorMessage = error
                }
            } else {
                errorMessage = "Por favor, completa todos los campos"
            }
            }) {
            Text("Iniciar sesión")
        }
        if (errorMessage != null){
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage!!)
        }
    }
}

fun signInWithEmailAndPassword(
    email: String,
    password: String,
    navController: NavController,
    onError: (String) -> Unit
    ){
    val auth = Firebase.auth
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if (task.isSuccessful){
                navController.navigate("home")
            } else {
                onError(task.exception?.message ?: "Error desconcido")
            }
        }
}