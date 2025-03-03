package com.wito.homapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wito.homapp.registerUser
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.wito.homapp.MainScreen

@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registrarse", style = MaterialTheme.typography.headlineSmall)

        // Campos de texto
        InputField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
        Spacer(modifier = Modifier.height(8.dp))

        InputField(value = password, onValueChange = { password = it }, label = "Contraseña", isPassword = true)
        Spacer(modifier = Modifier.height(8.dp))

        InputField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = "Confirmar contraseña", isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))

        // Botón de registro
        AppButton(text = "Registrarse") {
            registerUser(email, password, confirmPassword)
        }
    }
}

// Componente para un campo de entrada (Input)
@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

// Componente para el botón
@Composable
fun AppButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}
