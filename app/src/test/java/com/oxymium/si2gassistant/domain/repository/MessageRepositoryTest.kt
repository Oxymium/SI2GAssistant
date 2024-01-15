package com.oxymium.si2gassistant.domain.repository

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreBugTicketsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreMessagesImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestorePersonsImpl
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.domain.mock.provideRandomMessage
import com.oxymium.si2gassistant.utils.observe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MessageRepositoryTest {

    // -----------------
    // GET: ALL MESSAGES
    // -----------------

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllMessagesFailureTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val messageCollectionMock = mockk<CollectionReference>()
        val messageRepository = FirebaseFirestoreMessagesImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns messageCollectionMock
        every { messageCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = messageRepository.getAllMessages().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Message>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllMessagesSuccessTest() = runTest {
        // GIVEN
        val message = provideRandomMessage()
        val documentId = "XdXddEbERRcDFg"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val messageCollectionMock = mockk<CollectionReference>()
        val messageRepository = FirebaseFirestoreMessagesImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val snapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        every { snapshot.documents } returns listOf(documentSnapshot)
        every { documentSnapshot.id } returns documentId
        every { documentSnapshot.toObject<Message>(any()) } returns message

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns messageCollectionMock
        every { messageCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(snapshot, null)
            registration
        }

        // WHEN
        val flow = messageRepository.getAllMessages().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Person>>(),
            Result.Success(listOf(message.copy(id = documentId)))
        )

        flow.finish()
    }

    // ---------------
    // SUBMIT: MESSAGE
    // ---------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitMessageFailureTest() = runTest {
        // GIVEN
        val givenMessage = provideRandomMessage()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val messageRepository = FirebaseFirestoreMessagesImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .add(any())
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            messageRepository.submitMessage(givenMessage)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitMessageSuccessTest() = runTest {
        // GIVEN
        val givenMessage = provideRandomMessage()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val messageRepository = FirebaseFirestoreMessagesImpl(firebaseFirestore)
        // Mocking the successful scenario
        val onSuccessListenerSlot = slot<OnSuccessListener<DocumentReference>>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .add(any())
                .addOnSuccessListener(capture(onSuccessListenerSlot))
                .addOnFailureListener(any())
        } answers {
            onSuccessListenerSlot.captured.onSuccess(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            messageRepository.submitMessage(givenMessage)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertFalse(exceptionThrown)
    }

    // ---------------
    // DELETE: MESSAGE
    // ---------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteMessageFailureTest() = runTest {
        // GIVEN
        val givenMessage = provideRandomMessage()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val messageRepository = FirebaseFirestoreMessagesImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .document()
                .delete()
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<Void>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            messageRepository.deleteMessage(givenMessage)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteMessageSuccessTest() = runTest {
        // GIVEN
        val givenMessage = provideRandomMessage()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val messageRepository = FirebaseFirestoreMessagesImpl(firebaseFirestore)
        // Mocking the successful scenario
        val onSuccessListenerSlot = slot<OnSuccessListener<Void>>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.MESSAGES)
                .document()
                .delete()
                .addOnSuccessListener(capture(onSuccessListenerSlot))
                .addOnFailureListener(any())
        } answers {
            onSuccessListenerSlot.captured.onSuccess(mockk(relaxed = true))
            mockk<Task<Void>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            messageRepository.deleteMessage(givenMessage)
            true
        } catch (e: Exception) {
            false
        }

        // THEN
        assertFalse(exceptionThrown)
    }

}