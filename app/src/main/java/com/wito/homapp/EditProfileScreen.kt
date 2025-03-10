package com.wito.homapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun EditProfileScreen(navController: NavController){
    val user = Firebase.auth.currentUser
    var name by remember { mutableStateOf(user?.displayName ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }

    BackHandler {
        // Cuando el usuario presione "atrás", navegamos a "home"
        navController.navigate("profile")
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Editar Nombre
        TextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        //Editar Email
        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )

        Button(
            onClick = {
                // Aquí iría el código para guardar los cambios en Firebase, por ejemplo.
                // Firebase.auth.currentUser?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
                // Puedes agregar aquí lógica para cambiar la foto, pero requiere un proceso adicional.
                navController.popBackStack() // Regresar a la pantalla de perfil
            }
        ) {
            Text("Guardar cambios")
        }

        // Botón para cancelar y regresar sin cambios
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text("Cancelar")
        }
    }
}