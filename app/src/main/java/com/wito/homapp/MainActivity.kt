package com.wito.homapp

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstancState: Bundle?){
        super.onCreate(savedInstancState)
        setContent {
            MainNavigator()
        }
    }
}

@Composable
fun MainNavigator(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainSplash"){
        composable("mainSplash") { SplashScreen(navController) }
    }
}

@Composable
fun SplashScreen(navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer( modifier = Modifier.height(16.dp))

        Text(text = "Confianza y calidad a menos de un click ;)", style = MaterialTheme.typography.headlineSmall)
        Spacer( modifier = Modifier.height(16.dp))

        Text(text = "¡Grandes profesionales te esperan en 'nombreApp'", style = MaterialTheme.typography.headlineSmall)
    }
}