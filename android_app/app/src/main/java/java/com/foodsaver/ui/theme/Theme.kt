package com.foodsaver.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF4CAF50),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF087F23),
    secondary = androidx.compose.ui.graphics.Color(0xFFFFC107)
)

@Composable
fun FoodSaverTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
