package com.wito.homapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wito.homapp.MainScreen
import com.wito.homapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ){
                    composable("main") { MainScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                }
                //MainScreen(navController = navController)
                //AuthStateHandler(navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
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
            Button(onClick = {navController.navigate("login") }) {
                Text("Iniciar Sesión")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("register") }) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("Olvidaste tu contraseña?", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MyAppTheme {
        MainScreen(
            navController = TODO()
        )
    }
}
