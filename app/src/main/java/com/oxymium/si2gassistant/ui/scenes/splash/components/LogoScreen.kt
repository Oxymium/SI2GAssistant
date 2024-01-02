package com.oxymium.si2gassistant.ui.scenes.splash.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.scenes.animations.SplashAnimation
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun LogoScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Neutral
            )
    ) {

        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
                .background(
                    color = Neutral
                )
        ) {

            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {

                Image(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = Neutral
                        ),
                    painter = painterResource(id = R.drawable.logo_app),
                    contentDescription = "app logo"
                )

                SplashAnimation()

            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun LogoScreenPreview() {
    Si2GAssistantTheme {
        LogoScreen()
    }
}