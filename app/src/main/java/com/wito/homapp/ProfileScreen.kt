package com.wito.homapp

import android.content.Context
import android.graphics.Paint.Style
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wito.homapp.ui.BottomNavBar


@Composable
fun ProfileScreen(navController: NavHostController){
    val context = LocalContext.current
    //val auth = FirebaseAuth.getInstance()
    val user = Firebase.auth.currentUser
    val username = user?.displayName ?: "Nombre no disponible"
    val userEmail = user?.email ?: "Correo no disponible"
    val userPhotoUrl = user?.photoUrl?.toString()

    //val navController = rememberNavController()





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userPhotoUrl != null){
            Image(
                painter = rememberAsyncImagePainter(model = userPhotoUrl),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        } else {
            Text("No hay foto de perfil")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Nombre del usuario
        Text(text = "Nombre: $username", style = MaterialTheme.typography.titleLarge, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        //Correo del usuario
        Text(text = "Correo: $userEmail", style = MaterialTheme.typography.bodyMedium, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Perfil de Usuario")

        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Añade espacio de 16dp entre los botones
            verticalAlignment = Alignment.CenterVertically // Alinea verticalmente los botones
        ){
            Button(onClick = {
                navController.navigate("editProfile")
            }) {
                Text("Editar perfil")
            }

            Button(onClick = {
                signOut(Firebase.auth, context, navController) // Llamamos a signOut para cerrar sesión en Firebase y Google
            }) {
                Text("Cerrar sesión")
            }
        }
        Button( modifier = Modifier.fillMaxWidth(),
            onClick = {
            navController.navigate("home")
        }) {
            Text("Volver")
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
