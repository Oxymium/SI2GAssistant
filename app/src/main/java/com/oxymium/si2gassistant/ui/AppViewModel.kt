package com.oxymium.si2gassistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.data.repository.DATASTORE_KEY_MAIL
import com.oxymium.si2gassistant.data.repository.DATASTORE_KEY_PASSWORD
import com.oxymium.si2gassistant.domain.entities.Login
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.repository.DataStoreRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import com.oxymium.si2gassistant.domain.states.AppState
import com.oxymium.si2gassistant.loadingInMillis
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

    init {
        loadLoginFromDatastore() // tries to load existing User from Datastore on launch
    }

    private fun loadLoginFromDatastore() {
        viewModelScope.launch {
            val mail = dataStoreRepository.getString(DATASTORE_KEY_MAIL)
            val password = dataStoreRepository.getString(DATASTORE_KEY_PASSWORD)
            val loadedLogin = Login(mail ?: "", password ?: "")
            // If any is empty, then no login to load into fields
            println(">>>>> $mail, $password")
            if (loadedLogin.mail.isNotEmpty() || loadedLogin.password.isNotEmpty()) {
                getAuthWithCredentials(
                    loadedLogin.mail,
                    loadedLogin.password
                )
            }
        }
    }

    private fun saveLoginIntoDatastore(mail: String, password: String) {
        viewModelScope.launch {
            dataStoreRepository.putLogin(
                Login(
                    mail = mail,
                    password = password
                )
            )
        }
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
                                isAuthLoading = false,
                            )
                        )
                    }
                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isAuthFailure = false,
                                authFailureMessage = null,
                                isAuthLoading = true,
                            )
                        )
                        delay(loadingInMillis)
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
                        // AUTH SUCCESS, CAN SAVE CREDENTIALS
                        saveLoginIntoDatastore(
                            mail = mail,
                            password = password
                        )

                        delay(loadingInMillis)
                        // Query User next
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
                                isUserLoading = false,
                            )
                        )
                    }

                    // LOADING
                    is Result.Loading -> {
                        _state.emit(
                            state.value.copy(
                                isUserFailure = false,
                                userFailureMessage = null,
                                isUserLoading = true,
                            )
                        )
                        delay(loadingInMillis)
                    }

                    // RESULT
                    is Result.Success -> {
                        // Save User credentials into Datastore
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
                saveLoginIntoDatastore(
                    event.mail,
                    event.password
                )
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