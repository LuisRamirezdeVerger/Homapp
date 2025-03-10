package com.wito.homapp

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



@Composable
fun ProfileScreen(navController: NavHostController){
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Perfil de Usuario")

        Button(onClick = {
            signOut(Firebase.auth, context, navController) // Llamamos a signOut para cerrar sesión en Firebase y Google
        }) {
            Text("Cerrar sesión")
        }
    }
}

fun signOut(auth: FirebaseAuth, context: Context, navController: NavHostController) {
    // Cierra sesión en Firebase
    auth.signOut()

    // Cierra sesión en Google
    val googleSignInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
    googleSignInClient.signOut().addOnCompleteListener {
        // Redirigr al usuario a la pantalla principal de login/registro
        navController.navigate("Main") {
            popUpTo("profile") { inclusive = true } // Evitar volver al perfil
        }
    }
}
