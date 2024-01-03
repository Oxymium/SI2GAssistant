package com.oxymium.si2gassistant.ui.scenes.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.data.repository.GLOBAL_USER
import com.oxymium.si2gassistant.domain.entities.Auth
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.usecase.FirebaseAuthState
import com.oxymium.si2gassistant.domain.usecase.UserState
import com.oxymium.si2gassistant.splashScreenDurationInMillis
import com.oxymium.si2gassistant.ui.scenes.splash.components.Login
import com.oxymium.si2gassistant.ui.scenes.splash.components.LoginValidator
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
            _state.emit(state.value.copy(
                isLogoScreen = false,
                isLoginScreen = true
            ))
        }
    }

    private fun getFirebaseAuthWithCredentials(mail: String, password: String) {
        viewModelScope.launch {
            authRepository.getFirebaseAuthWithCredentials(mail, password)
                .collect { firebaseUserState ->
                    when (firebaseUserState) {

                        // LOADING
                        FirebaseAuthState.Loading -> {
                            val newState = state.value.copy(
                                isAuthLoading = true,
                                isAuthSuccessful = false,
                                authError = AuthError(isError = false, "")
                            )
                            _state.emit(newState)
                            delay(900L)
                        }

                        // FAILURE
                        is FirebaseAuthState.Failure -> {
                            val newState = state.value.copy(
                                isAuthLoading = false, // loading complete
                                isAuthSuccessful = false,
                                authError = AuthError(true, firebaseUserState.exception.message)
                            )
                            _state.emit(newState)
                            delay(900L)
                        }

                        // SUCCESS
                        is FirebaseAuthState.Success -> {
                            val newState = state.value.copy(
                                isAuthLoading = false,
                                isAuthSuccessful = true,
                                auth = Auth(firebaseUserState.firebaseUser?.displayName ?: "", firebaseUserState.firebaseUser?.email ?: ""),
                            )
                            _state.emit(newState)
                            delay(900L)
                            // Retrieve Firebase Firebase User based on Auth mail
                            firebaseUserState.firebaseUser?.uid?.let { uid -> getFirebaseFirestoreUserWithUid(uid) }
                        }
                        null -> TODO()
                    }
                }
        }
    }

    private fun getFirebaseFirestoreUserWithUid(uid: String) {
        viewModelScope.launch {
            userRepository.getUserByUid(uid).collect { userState ->
                when (userState) {

                    // LOADING
                    UserState.Loading -> _state.value = state.value.copy(
                        isUserLoading = true,
                        isUserSuccessful = false,
                    )

                    // ERROR
                    is UserState.Error -> _state.value = state.value.copy(
                        isUserLoading = false,
                        isUserSuccessful = false,
                        userError = userState.exception.message
                    )

                    // SUCCESS
                    is UserState.Success -> {
                        _state.value = state.value.copy(
                            isUserLoading = false,
                            isUserSuccessful = true,
                            user = userState.user
                        )
                        GLOBAL_USER = userState.user // TODO remove global variable
                    }

                }
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {

            SplashEvent.OnClickLoginButton -> {
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

            is SplashEvent.OnLoginMailChanged -> _login.value = login.value.copy(email = event.mail)

            is SplashEvent.OnLoginPasswordChanged -> _login.value = login.value.copy(password = event.password)

        }
    }
}