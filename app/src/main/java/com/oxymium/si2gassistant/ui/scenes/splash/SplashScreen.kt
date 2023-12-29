package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.scenes.NavigationEvent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.NeutralLighter
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun SplashScreen(
    navigationEvent: (NavigationEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Neutral
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(
                    color = NeutralLighter,
                    shape = RoundedCornerShape(
                        bottomStart = 135.dp,
                        bottomEnd = 135.dp
                    )
                )
        ) {

            Button(
                modifier = Modifier
                    .align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutral
                ),
                onClick = { navigationEvent.invoke(NavigationEvent.OnSplashStartButtonClicked) }
            ) {
                Icon(
                    modifier = Modifier
                        .background(Neutral)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_power),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.9f)
                .align(Alignment.Center)
        ) {
            Image(
                painterResource(id = R.drawable.logo_app),
                modifier = Modifier
                    .fillMaxSize(),
                contentDescription = null,
            )
        }

    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Si2GAssistantTheme {
        SplashScreen() {

        }
    }
}