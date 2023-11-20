package com.oxymium.si2gassistant.ui.scenes.splash.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SplashLogo(){
    Box(
        modifier = Modifier
    ) {
        Image(
            painterResource(id = R.drawable.logo_app),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashLogoPreview(){
    Si2GAssistantTheme {
        SplashLogo()
    }
}