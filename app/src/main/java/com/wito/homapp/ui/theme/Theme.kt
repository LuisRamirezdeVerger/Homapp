package com.wito.homapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5),
)

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightThemeColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
