// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) version "1.9.22" apply false // Define la versión de Kotlin aquí
    id("com.google.gms.google-services") version "4.4.2" apply false // Plugin de Google Services
}

// Configura los repositorios para todos los módulos
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

// Tarea para limpiar el proyecto
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}