package com.oxymium.si2gassistant.ui.scenes.greetings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.scenes.greetings.components.GreetingsMessage

@Composable
fun GreetingsScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier
        ,
        color = Color.Blue
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .background(
                        color = Color.Magenta,
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 135.dp,
                            bottomEnd = 135.dp
                        )
                    )
            )
        }

        GreetingsMessage()

        // Top right corner decoration
        Box(
            modifier = Modifier
                .fillMaxWidth(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.hexagonal_grid),
                contentDescription = "test",
            )
        }

        //      // Bottom left corner decoration
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                modifier = Modifier
                    .scale(0.5f),
                painter = painterResource(id = R.drawable.hexagonal_grid),
                contentDescription = "test",
                colorFilter = ColorFilter.tint(Color.Red),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    GreetingsScreen()
}