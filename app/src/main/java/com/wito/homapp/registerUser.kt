package com.wito.homapp

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Clase para gestionar el registro de un usuario
class registerUser(email: String, password: String, confirmPassword: String) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Función para registrar al usuario con su correo y contraseña
    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = withContext(Dispatchers.IO) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            }
            result.user
        } catch (e: Exception) {
            Log.e("RegisterUser", "Error en el registro: ${e.message}")
            null
        }
    }
}
