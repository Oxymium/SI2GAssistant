package com.oxymium.si2gassistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.repository.DataStoreRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.states.AppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

//    private fun saveLogin(mail: String, password: String) {
//        viewModelScope.launch {
//            dataStoreRepository.putLogin(com.oxymium.si2gassistant.domain.entities.Login(mail, password))
//        }
//    }
//
//    private fun loadLogin() {
//        viewModelScope.launch {
//            val mail = dataStoreRepository.getString(DATASTORE_KEY_MAIL)
//            println(">>>>>> $mail")
//            val password = dataStoreRepository.getString(DATASTORE_KEY_PASSWORD)
//            println(">>>>>> $password")
//            val loadedLogin = com.oxymium.si2gassistant.domain.entities.Login(mail ?: "", password ?: "")
//            // If any is empty, then no login to load into fields
//            if (loadedLogin.mail.isEmpty() || loadedLogin.password.isEmpty()) {
//                _state.emit(
//                    state.value.copy(
//                        rememberedMail = null,
//                        rememberedPassword = null
//                    )
//                )
//            } else {
//                _state.emit(
//                    state.value.copy(
//                        rememberedMail = mail,
//                        rememberedPassword = password
//                    )
//                )
//            }
//        }
//    }
//

    private fun resetState() {
        _state.value =
            state.value.copy(

            )
    }
    private fun getAuthWithCredentials(mail: String, password: String) {
        viewModelScope.launch {
            authRepository.getFirebaseAuthWithCredentials(mail, password).collect {
                when (it) {
                    // FAILURE
                    is Result.Failed -> {
                        _state.emit(
                            state.value.copy(
                                isAuthFailure = true,
                                authFailureMessage = it.errorMessage,
                                isAuthLoading = false
                            )
                        )
                    }
                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isAuthFailure = false,
                                authFailureMessage = null,
                                isAuthLoading = true
                            )
                        )
                        delay(500L)
                    }
                    // RESULT
                    is Result.Success -> {
                        _state.emit(
                            state.value.copy(
                                isAuthFailure = false,
                                authFailureMessage = null,
                                isAuthLoading = false,
                            )
                        )
                        it.data?.let { user -> getUserByUid(user.uid) }
                    }
                }
            }
        }
    }

    private fun getUserByUid(uid: String) {
        viewModelScope.launch {
            userRepository.getUserByUid(uid).collect {
                when (it) {
                    // FAILURE
                    is Result.Failed -> {
                        _state.emit(
                            state.value.copy(
                                isUserFailure = true,
                                userFailureMessage = it.errorMessage,
                            )
                        )
                    }

                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isUserFailure = false,
                                userFailureMessage = null,
                                isUserLoading = true
                            )
                        )
                        delay(500L)
                    }

                    // RESULT
                    is Result.Success -> {
                        _state.emit(
                            state.value.copy(
                                isUserFailure = false,
                                userFailureMessage = null,
                                isUserLoading = false,
                                currentUser = it.data
                            )
                        )
                        currentUser = it.data
                    }

                }
            }
        }
    }

    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.OnLoginButtonClick -> {
                getAuthWithCredentials(
                    mail = event.mail,
                    password = event.password
                )
            }

            is AppEvent.OnLogoutButtonClick -> {
                _state.value =
                    state.value.copy(
                        currentUser = null
                    )
            }

            is AppEvent.OnItemMenuButtonClick -> {
                _state.value =
                    state.value.copy(
                        currentScreen = event.appScreen
                    )
            }

        }
    }

}