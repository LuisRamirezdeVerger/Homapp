package com.wito.homapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wito.homapp.ui.GoogleSignInButton
import com.wito.homapp.ui.signInWithGoogle
import com.wito.homapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "authStateHandler"
                ) {
                    composable("authStateHandler") {
                        AuthStateHandler(navController) // Verifica el estado de autenticación
                    }
                    composable("main") { MainScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home"){ HomeScreen(navController) }
                    composable("profile"){ProfileScreen(navController)}
                    composable("editProfile"){EditProfileScreen(navController)}
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val googleSignInLauncher: ActivityResultLauncher<Intent> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            handleGoogleSignInResult(result.data, auth, navController)
        } else {
            Log.e("GoogleSignIn", "Google Sign-In cancelado o fallido")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_bewel), // Asegúrate de tener un logo en res/drawable
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Bienvenido a la App", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate("login") }) {
                Text("Iniciar Sesión")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("register") }) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Botón de Google
            GoogleSignInButton(onClick = { signInWithGoogle(context, googleSignInLauncher) })

            Spacer(modifier = Modifier.height(32.dp))
            Text("Olvidaste tu contraseña?", color = Color.White)
        }
    }
}

fun handleGoogleSignInResult(data: Intent?, auth: FirebaseAuth, navController: NavHostController) {
    Log.d("GoogleSignIn", "Intent recibido, procesando...") // 👈 DEBUG

    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    try {
        val account = task.getResult(ApiException::class.java)
        Log.d("GoogleSignIn", "Cuenta obtenida: ${account.email}") // 👈 DEBUG
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("GoogleSignIn", "Inicio de sesión exitoso: ${user?.email}")

                    // Si el usuario es nuevo, lo agrega a la base de datos
                    if (task.result?.additionalUserInfo?.isNewUser == true) {
                        Log.d("GoogleSignIn", "Usuario nuevo detectado, registrándolo...")
                        registerUserInDatabase(user)
                    }

                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                } else {
                    Log.e("GoogleSignIn", "Error al iniciar sesión con Google", task.exception)
                }
            }
    } catch (e: ApiException) {
        Log.e("GoogleSignIn", "Error en el inicio de sesión: ${e.statusCode}")
    }
}

// Función para registrar usuario en la base de datos si es nuevo
fun registerUserInDatabase(user: FirebaseUser?) {
    user?.let {
        val db = Firebase.firestore
        val userDoc = db.collection("users").document(user.uid)

        val userData = hashMapOf(
            "uid" to user.uid,
            "email" to user.email,
            "displayName" to user.displayName
        )

        userDoc.set(userData)
            .addOnSuccessListener { Log.d("GoogleSignIn", "Usuario registrado en la base de datos") }
            .addOnFailureListener { e -> Log.e("GoogleSignIn", "Error al registrar usuario: ${e.message}") }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MyAppTheme {
        val navController = rememberNavController()
        MainScreen(navController = navController)
    }
}