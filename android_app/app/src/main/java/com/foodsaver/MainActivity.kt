package com.foodsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.foodsaver.ui.LoginScreen
import com.foodsaver.ui.theme.FoodSaverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodSaverTheme {
                LoginScreen()
            }
        }
    }
}
