package com.oxymium.si2gassistant.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Si2GAssistantTheme {
                val navHostController = rememberNavController()
                NavHostController(
                    navHostController = navHostController,
                )
            }
        }
    }
}