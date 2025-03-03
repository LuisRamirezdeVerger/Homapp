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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wito.homapp.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Asegúrate de tener un logo en res/drawable
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Bienvenido a la App", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Acción 1 */ }) {
                Text("Botón 1")
            }
            Button(onClick = { /* Acción 2 */ }) {
                Text("Botón 2")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MyAppTheme {
        MainScreen()
    }
}
