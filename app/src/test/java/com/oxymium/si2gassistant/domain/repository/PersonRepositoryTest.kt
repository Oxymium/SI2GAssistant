package com.oxymium.si2gassistant.domain.repository

import com.google.common.truth.Truth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.oxymium.si2gassistant.data.repository.FirebaseFirestorePersonsImpl
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomPerson
import com.oxymium.si2gassistant.utils.observe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PersonRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPersonsErrorTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personCollectionMock = mockk<CollectionReference>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns personCollectionMock
        every { personCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = personRepository.getAllPersons().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Person>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPersonsSuccessTest() = runTest {
        // GIVEN
        val person = provideRandomPerson()
        val documentId = "XdbERRcDFg"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personCollectionMock = mockk<CollectionReference>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val snapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        every { snapshot.documents } returns listOf(documentSnapshot)
        every { documentSnapshot.id } returns documentId
        every { documentSnapshot.toObject<Person>(any()) } returns person

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns personCollectionMock
        every { personCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(snapshot, null)
            registration
        }

        // WHEN
        val flow = personRepository.getAllPersons().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Person>>(),
            Result.Success(listOf(person.copy(id = documentId)))
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPersonsByUserIdFailureTest() = runTest {}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPersonsByUserIdSuccessTest() = runTest {}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitPersonFailureTest() = runTest {}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitPersonSuccessTest() = runTest {}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updatePersonFailureTest() = runTest {}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updatePersonSuccessTest() = runTest {}

}