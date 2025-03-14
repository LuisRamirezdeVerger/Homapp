package com.wito.homapp.ui

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.wito.homapp.registerUser
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.wito.homapp.MainScreen
import com.wito.homapp.R

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

@Composable
fun GoogleSignInButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon (
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = "Google Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continuar con Google")
        }
    }
}


fun signInWithGoogle(
    context: Context,
    googleSignInlauncher: ActivityResultLauncher<Intent>,
    //auth: FirebaseAuth,
    //navController: NavHostController
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id)) // Obtén el ID del cliente desde google-services.json
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInIntent = googleSignInClient.signInIntent
    googleSignInlauncher.launch(signInIntent)
}

@Composable
fun BottomNavBar(navHostController: NavHostController){
    val items = listOf(
        NavItem("Buscar", Icons.Default.Search, "search"),
        NavItem("Servicios", Icons.Default.Build, "services"),
        NavItem("Chat", Icons.Default.Call, "chat"),
        NavItem("Favoritos", Icons.Default.Favorite, "favorites"),
        NavItem("Perfil", Icons.Default.Person, "profile")
    )

    NavigationBar {
        val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach{ items ->
            NavigationBarItem(
                icon = { Icon (items.icon, contentDescription = items.label) },
                label = {Text (items.label)},
                selected = currentRoute == items.route,
                onClick = {
                    navHostController.navigate(items.route) {
                        //popUpTo(navHostController.graph.startDestinationId){saveState = true}
                        popUpTo("home") {inclusive = false}
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
data class NavItem(val label: String, val icon: ImageVector, val route: String)



@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}
