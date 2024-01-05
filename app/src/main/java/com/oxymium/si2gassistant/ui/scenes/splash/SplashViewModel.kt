package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.data.repository.GLOBAL_USER
import com.oxymium.si2gassistant.domain.entities.Auth
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.states.AuthError
import com.oxymium.si2gassistant.domain.states.SplashState
import com.oxymium.si2gassistant.loadingInMillis
import com.oxymium.si2gassistant.splashScreenDurationInMillis
import com.oxymium.si2gassistant.domain.validators.Login
import com.oxymium.si2gassistant.domain.validators.LoginValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
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

    private fun getFirebaseAuthWithCredentials(mail: String, password: String) {
        viewModelScope.launch {
            authRepository.getFirebaseAuthWithCredentials(mail, password).collect {
                    when (it) {

                        // FAILURE
                        is Result.Failed -> {
                            _state.emit(
                                state.value.copy(
                                    isAuthLoading = false, // loading complete
                                    isAuthSuccessful = false,
                                    authError = AuthError(isError = true, it.errorMessage)
                                )
                            )
                            delay(900L)
                        }

                        // LOADING
                        is Result.Loading -> {
                            _state.emit(
                                state.value.copy(
                                    isAuthLoading = true,
                                    isAuthSuccessful = false,
                                    authError = AuthError(isError = false, null)
                                )
                            )
                            delay(loadingInMillis)
                        }

                        // SUCCESS
                        is Result.Success -> {
                            _state.emit(
                                state.value.copy(
                                    isAuthLoading = false,
                                    isAuthSuccessful = true,
                                    auth = Auth( it.data?.displayName ?: "", it.data?.email ?: ""),
                                )
                            )
                            delay(loadingInMillis)
                            // Retrieve Firebase Firebase User based on Auth mail
                            it.data?.uid?.let { uid -> getFirebaseFirestoreUserWithUid(uid)  }
                        }
                    }
                }
        }
    }

    private fun getFirebaseFirestoreUserWithUid(uid: String) {
        viewModelScope.launch {
            userRepository.getUserByUid(uid).collect {
                when (it) {

                    // FAILURE
                    is Result.Failed -> _state.emit(
                        state.value.copy(
                            isUserLoading = false,
                            isUserSuccessful = false,
                            userError = it.errorMessage
                        )
                    )

                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isUserLoading = true,
                                isUserSuccessful = false
                        )
                    )
                        delay(loadingInMillis)
                    }

                    // SUCCESS
                    is Result.Success -> {
                        _state.emit(
                            state.value.copy(
                                isUserLoading = false,
                                isUserSuccessful = true,
                                user = it.data
                            )
                        )

                        GLOBAL_USER = it.data // push user as global variable
                    }

                }
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {

            SplashEvent.OnLoginButtonClick -> {

                val result = LoginValidator.validateLogin(login.value)
                // Verify if any element is null
                val errors = listOfNotNull(
                    result.loginMailError,
                    result.loginPasswordError
                )
                if (errors.isEmpty()) {
                    _state.value = state.value.copy(
                        isMailFieldError = false,
                        isPasswordFieldError = false,
                        login = null
                    )
                    getFirebaseAuthWithCredentials(
                        login.value.email,
                        login.value.password
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