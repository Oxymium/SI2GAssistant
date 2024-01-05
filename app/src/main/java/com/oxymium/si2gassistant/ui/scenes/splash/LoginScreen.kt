package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.Auth
import com.oxymium.si2gassistant.domain.states.SplashState
import com.oxymium.si2gassistant.ui.navigation.NavigationEvent
import com.oxymium.si2gassistant.ui.scenes.animations.LoadingAnimation
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.TextAccent
import com.oxymium.si2gassistant.ui.theme.White

@Composable
fun LoginScreen(
    state: SplashState,
    navigationEvent: (NavigationEvent) -> Unit,
    event: (SplashEvent) -> Unit
) {

    if (state.isAuthSuccessful) { navigationEvent.invoke(NavigationEvent.OnLoginButtonClick(state.user)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Neutral
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .background(
                        color = Neutral,
                    )
            ) {

                // LOGIN BUTTON
                if (state.isUserLoading) { // toggled off during loading

                    Button(
                        modifier = Modifier
                            .align(Alignment.Center),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MenuAccent
                        ),
                        onClick = { event.invoke(SplashEvent.OnLoginButtonClick) }
                    ) {

                        Icon(
                            modifier = Modifier
                                .background(MenuAccent)
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.ic_login_variant),
                            contentDescription = "Login button",
                            tint = White
                        )
                    }

                }

            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                Column {

                    // TITLE
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                            .fillMaxWidth(),
                        text = "Credentials",
                        color = White,
                        textAlign = TextAlign.End
                    )

                    // MAIL
                    var mail by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = mail,
                            onValueChange = {
                                mail = it.filter { char -> !char.isWhitespace() }.take(40)
                                event.invoke(SplashEvent.OnLoginMailChange(mail))
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = White,
                                cursorColor = White,
                                focusedBorderColor = TextAccent,
                                unfocusedBorderColor = White
                            ),
                            label = {
                                Text(
                                    text = "Mail",
                                    color = Color.White
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Email
                            ),
                            maxLines = 1
                        )
                    }

                    if (state.isMailFieldError) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                                .fillMaxWidth(),
                            text = "*mail cannot be empty",
                            color = White,
                            textAlign = TextAlign.End
                        )
                    }

                    // PASSWORD
                    var password by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = password,
                            onValueChange = {
                                password = it.filter { char -> !char.isWhitespace() }.take(20)
                                event.invoke(SplashEvent.OnLoginPasswordChange(password))
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = White,
                                cursorColor = White,
                                focusedBorderColor = TextAccent,
                                unfocusedBorderColor = White,
                            ),
                            label = {
                                Text(
                                    text = "Password",
                                    color = White
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Password
                            ),
                            maxLines = 1,
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }

                    if (state.isPasswordFieldError) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                                .fillMaxWidth(),
                            text = "*password cannot be empty",
                            color = White,
                            textAlign = TextAlign.End
                        )
                    }

                    // LOADING ANIMATION
                    if (state.isAuthLoading) {

                        LoadingAnimation(
                            modifier = Modifier
                                .fillMaxSize()
                        )

                    }

                }

                val offsetY1 by animateFloatAsState(
                    targetValue = if (state.isAuthLoading) 0f else 500f,
                    animationSpec = tween(durationMillis = 500), label = ""
                )

                val offsetY2 by animateFloatAsState(
                    targetValue = if (state.isAuthSuccessful) 0f else 500f,
                    animationSpec = tween(durationMillis = 500), label = ""
                )

                val offsetY3 by animateFloatAsState(
                    targetValue = if (state.authError?.isError == true) 0f else 500f,
                    animationSpec = tween(durationMillis = 500), label = ""
                )

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {

                    if (state.isAuthLoading) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = offsetY1.dp),
                            text = "Loading...",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (state.isAuthSuccessful) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = offsetY2.dp),
                            text = "Successful!",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                        )
                    }

                    if (state.authError?.isError == true) {
                        Column() {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y = offsetY3.dp),
                                text = "Failure",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y = offsetY3.dp),
                                text = "${state.authError.errorMessage}",
                                color = White,
                                textAlign = TextAlign.Center,
                            )
                        }

                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val loginPreview = Auth("mail@mock.test", "Random")
    val state = SplashState(auth = loginPreview)
    Si2GAssistantTheme() {
        LoginScreen(
            state = state,
            navigationEvent = {},
            event = {}
        )
    }
}