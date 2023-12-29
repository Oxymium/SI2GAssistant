package com.oxymium.si2gassistant.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen() // needs to be called before setContent()
        setContent {
            Si2GAssistantTheme {
                val navHostController = rememberNavController()

                App(
                    navController = navHostController
                )

            }
        }
    }
}