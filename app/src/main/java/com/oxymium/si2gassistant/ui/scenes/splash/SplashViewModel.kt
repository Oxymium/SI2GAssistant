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
    val login = _login.asStateFlow()

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

            SplashEvent.OnButtonClickCallBack -> {
                _state.value =
                    state.value.copy(
                        authQuery = null
                    )
            }

            SplashEvent.OnLoginButtonClick -> {

                val login = Login(
                    _login.value.email,
                    _login.value.password
                )

                val result = LoginValidator.validateLogin(login)
                // Verify if any element is null
                val errors = listOfNotNull(
                    result.loginMailError,
                    result.loginPasswordError
                )
                if (errors.isEmpty()) {
                    _state.value = state.value.copy(
                        isMailFieldError = false,
                        isPasswordFieldError = false,
                    )

                    val authQuery = AuthQuery(
                        isReady = true,
                        mail = login.email,
                        password = login.password
                    )

                    // Can start LOGIN
                    _state.value =
                        state.value.copy(
                            authQuery = authQuery
                        )

                } else {
                    if (!result.loginMailError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isMailFieldError = true
                        )
                    }
                    if (!result.loginPasswordError.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            isPasswordFieldError = true
                        )
                    }
                }
            }

            is SplashEvent.OnLoginMailChange -> {
                _login.value =
                    login.value.copy(
                        email = event.mail
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