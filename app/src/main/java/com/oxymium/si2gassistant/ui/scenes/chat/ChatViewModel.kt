package com.oxymium.si2gassistant.ui.scenes.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.currentUser
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.repository.MessageRepository
import com.oxymium.si2gassistant.domain.validators.MessageValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ChatViewModel(
    private val messageRepository: MessageRepository
): ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private val _message = MutableStateFlow(Message())
    val message = _message.asStateFlow()

    init {
        getAllMessages()
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            messageRepository.getAllMessages().collect {
                when (it) {
                    is Result.Failed -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        _state.value =
                            state.value.copy(
                                messages = it.data.sortedBy { message -> message.submittedDate }.asReversed()
                            )
                    }
                }
            }
        }
    }

    private fun submitMessage() {
        viewModelScope.launch {
            val finalizedMessage = _message.value.copy(
                submittedBy = currentUser?.mail,
                submittedDate = Calendar.getInstance().timeInMillis
            )
            messageRepository.submitMessage(message = finalizedMessage).collect {
                when (it) {
                    is Result.Failed -> {}
                    is Result.Loading -> {}
                    is Result.Success -> {}
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {

            is ChatEvent.OnContentChange -> {
                _message.value =
                    message.value.copy(
                        content = event.content
                    )
            }

            ChatEvent.OnSubmitMessageButtonClick -> {

                val validator = MessageValidator.validateBugTicket(message.value)
                if (validator.hasErrors()) {
                    _state.value =
                        state.value.copy(
                            isContentFieldError = true
                        )
                } else {
                    submitMessage()
                }
            }

        }
    }

}