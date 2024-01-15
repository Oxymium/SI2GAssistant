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
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreMessagesImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestorePersonsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreSuggestionsImpl
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.mock.provideRandomMessage
import com.oxymium.si2gassistant.domain.mock.provideRandomSuggestion
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
import kotlin.test.assertTrue

class SuggestionRepositoryTest {

    // ---------------------
    // GET : ALL SUGGESTIONS
    // ---------------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllSuggestionsErrorTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val suggestionCollectionMock = mockk<CollectionReference>()
        val suggestionRepository = FirebaseFirestoreSuggestionsImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns suggestionCollectionMock
        every { suggestionCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = suggestionRepository.getAllSuggestions().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Suggestion>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllSuggestionsSuccessTest() = runTest {
        // GIVEN
        val suggestion = provideRandomSuggestion()
        val documentId = "Er0DrxAPxE"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val suggestionCollectionMock = mockk<CollectionReference>()
        val suggestionRepository = FirebaseFirestoreSuggestionsImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val snapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        every { snapshot.documents } returns listOf(documentSnapshot)
        every { documentSnapshot.id } returns documentId
        every { documentSnapshot.toObject<Suggestion>(any()) } returns suggestion

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns suggestionCollectionMock
        every { suggestionCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(snapshot, null)
            registration
        }

        // WHEN
        val flow = suggestionRepository.getAllSuggestions().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Suggestion>>(),
            Result.Success(listOf(suggestion.copy(id = documentId)))
        )

        flow.finish()

    }

    // -------------------
    // SUBMIT : SUGGESTION
    // -------------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitSuggestionFailureTest() = runTest {
        // GIVEN
        val givenSuggestion = provideRandomSuggestion()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val suggestionRepository = FirebaseFirestoreSuggestionsImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.SUGGESTIONS)
                .add(any())
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            suggestionRepository.submitSuggestion(givenSuggestion)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitSuggestionSuccessTest() = runTest {
        // GIVEN
        val givenSuggestion = provideRandomSuggestion()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val suggestionRepository = FirebaseFirestoreSuggestionsImpl(firebaseFirestore)
        // Mocking the successful scenario
        val onSuccessListenerSlot = slot<OnSuccessListener<DocumentReference>>()
        coEvery {
            firebaseFirestore.collection(FirebaseFirestoreCollections.PERSONS)
                .add(any())
                .addOnSuccessListener(capture(onSuccessListenerSlot))
                .addOnFailureListener(any())
        } answers {
            onSuccessListenerSlot.captured.onSuccess(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            suggestionRepository.submitSuggestion(givenSuggestion)
            true
        } catch (e: Exception) {
            false
        }

        // THEN
        TestCase.assertFalse(exceptionThrown)
    }

}