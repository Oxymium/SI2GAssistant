package com.oxymium.si2gassistant.ui.scenes.chat

import com.google.common.truth.Truth
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomMessage
import com.oxymium.si2gassistant.domain.repository.MessageRepository
import com.oxymium.si2gassistant.utils.TestCoroutineRule
import com.oxymium.si2gassistant.utils.observe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChatViewModelTest {

    private val messageRepository = mockk<MessageRepository>()

    private lateinit var chatViewModel: ChatViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatchersRule = TestCoroutineRule()

    @Before
    fun setup() {
        chatViewModel = ChatViewModel(messageRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on message content change event`() = runTest {
        // GIVEN
        val message = chatViewModel.message.observe(this)
        val givenString = "Test string"
        every { messageRepository.getAllMessages() } returns flow { Result.Success(emptyList<List<Message>>()) }

        // WHEN
        chatViewModel.onEvent(
            ChatEvent.OnContentChange(givenString)
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(message.values).containsExactly(
            Message(),
            Message(null, null, null, givenString)
        )
        message.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test on submit message button click validated event`() = runTest {
        // GIVEN
        val state = chatViewModel.state.observe(this)
        every { messageRepository.getAllMessages() } returns flow { Result.Success(emptyList<List<Message>>()) }

        // WHEN
        chatViewModel.onEvent(
            ChatEvent.OnSubmitMessageButtonClick
        )
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            ChatState(),
            ChatState(
                messages = emptyList(),
                isContentFieldError = true
            )
        )

        state.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test get all messages success`() = runTest {
        // GIVEN
        val givenMessages = List (5) { provideRandomMessage() }
        val state = chatViewModel.state.observe(this)
        every { messageRepository.getAllMessages() } returns flow { Result.Success(givenMessages) }

        // WHEN
        val messages = when (val result = messageRepository.getAllMessages().first()) {
            is Result.Success -> result.data
            else -> emptyList()
        }
        advanceUntilIdle()

        // THEN
        Truth.assertThat(state.values).containsExactly(
            ChatState(),
            ChatState(messages = messages)
        )

        state.finish()
    }

}