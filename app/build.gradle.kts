plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
    //id("com.android.application")
    //id("com.google.gms.google-services")
}

android {
    namespace = "com.wito.homapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.wito.homapp"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    // Jetpack Compose
    implementation(libs.compiler.v158)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // Navigation con Compose
    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(libs.compiler)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}