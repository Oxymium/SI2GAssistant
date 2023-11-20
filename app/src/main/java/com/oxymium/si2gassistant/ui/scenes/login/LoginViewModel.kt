package com.oxymium.si2gassistant.ui.scenes.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.usecase.FirebaseUserState
import com.oxymium.si2gassistant.domain.entities.Auth
import com.oxymium.si2gassistant.domain.repository.FirebaseUserRepository
import com.oxymium.si2gassistant.domain.usecase.LoginError
import com.oxymium.si2gassistant.domain.usecase.LoginEvent
import com.oxymium.si2gassistant.domain.usecase.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val firebaseUserRepository: FirebaseUserRepository): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private fun getFirebaseUserWithCredentials(mail: String, password: String) {
        viewModelScope.launch {
            firebaseUserRepository.getFirebaseUserWithCredentials(mail, password)
                .collect { firebaseUserState ->
                    when (firebaseUserState) {
                        // LOADING
                        FirebaseUserState.Loading -> {
                            val newState = state.value.copy(isLoading = true)
                            _state.emit(newState)
                        }
                        // FAILURE
                        is FirebaseUserState.Failure -> {
                            val newState = state.value.copy(
                                isLoading = false, // loading complete, resets
                                isSuccessful = false,
                                loginError = LoginError(true, firebaseUserState.exception.message)
                            )
                            _state.emit(newState)
                        }
                        // SUCCESS
                        is FirebaseUserState.Success -> {
                            val newState = state.value.copy(
                                isLoading = false,
                                auth = Auth(firebaseUserState.firebaseUser?.displayName ?: "", firebaseUserState.firebaseUser?.email ?: ""),
                            )
                            _state.emit(newState)
                        }
                        null -> TODO()
                    }
                }
        }
    }

    fun onEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            LoginEvent.OnClickLoginButton -> getFirebaseUserWithCredentials("gmail@gmail.test", "azerty")
        }
    }
}