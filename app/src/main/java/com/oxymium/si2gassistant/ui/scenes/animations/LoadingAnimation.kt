package com.oxymium.si2gassistant.ui.scenes.animations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun LoadingAnimation() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.lottie_loading_animation))
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

    }

}

@Preview(showBackground = true)
@Composable
fun LoadingAnimationPreview() {
    Si2GAssistantTheme {
        LoadingAnimation()
    }
}
