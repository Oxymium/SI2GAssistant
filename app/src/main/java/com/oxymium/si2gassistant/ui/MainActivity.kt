package com.oxymium.si2gassistant.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.oxymium.si2gassistant.ui.navigation.NavHost
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen() // needs to be called before setContent()

        setContent {

            Si2GAssistantTheme {
                val navHostController = rememberNavController()

                NavHost(
                    navController = navHostController
                )

            }
        }

    }
}