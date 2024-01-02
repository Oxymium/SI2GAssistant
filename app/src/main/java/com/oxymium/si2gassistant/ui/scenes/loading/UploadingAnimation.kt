package com.oxymium.si2gassistant.ui.scenes.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oxymium.si2gassistant.R

@Composable
fun UploadingAnimation() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.lottie_uploading_animation))
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

    }

}