package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.repository.DataStoreRepository
import com.oxymium.si2gassistant.domain.states.AuthQuery
import com.oxymium.si2gassistant.domain.states.SplashState
import com.oxymium.si2gassistant.domain.validators.Login
import com.oxymium.si2gassistant.domain.validators.LoginValidator
import com.oxymium.si2gassistant.splashScreenDurationInMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    private val _login = MutableStateFlow(Login("", ""))
    private val login = _login.asStateFlow()

    init {
        displayLogoScreenForGivenDuration() // start coroutine initially with given duration
    }

    private fun displayLogoScreenForGivenDuration() {
        viewModelScope.launch {
            delay(splashScreenDurationInMillis)
            _state.emit(
                state.value.copy(
                    isLogoScreen = false,
                    isLoginScreen = true
                )
            )
        }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {

            SplashEvent.OnButtonClickCallback -> {
                _state.value =
                    state.value.copy(
                        authQuery = null
                    )
            }

            SplashEvent.OnLoginButtonClick -> {

                    // Initial reset
                    _state.value =
                        state.value.copy(
                            isMailFieldError = false,
                            isPasswordFieldError = false
                        )

                    val login = Login(
                        _login.value.mail,
                        _login.value.password
                    )

                    val result = LoginValidator.validateLogin(login)

                    // Check if any errors
                    if (result.hasErrors()) {
                        _state.value =
                            state.value.copy(
                                isMailFieldError = result.loginMailError,
                                isPasswordFieldError = result.loginPasswordError
                            )
                    } else {
                        // Can start LOGIN & reset field errors
                        _state.value =
                            state.value.copy(
                                authQuery = AuthQuery(
                                    isReady = true,
                                    mail = login.mail,
                                    password = login.password
                                )
                            )
                    }

            }

            is SplashEvent.OnLoginMailChange -> {
                _login.value =
                    login.value.copy(
                        mail = event.mail
                    )
            }

            is SplashEvent.OnLoginPasswordChange -> {
                _login.value =
                    login.value.copy(
                        password = event.password
                    )
            }

        }

    }

}