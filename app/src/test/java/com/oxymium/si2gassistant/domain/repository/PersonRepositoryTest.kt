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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.oxymium.si2gassistant.data.repository.FirebaseFirestorePersonsImpl
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreFields
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomPerson
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

class PersonRepositoryTest {

    // ----------------
    // GET: ALL PERSONS
    // ----------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPersonsFailureTest() = runTest {
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

    // ----------------------
    // GET: ALL PERSONS BY ID
    // ----------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPersonsByUserIdFailureTest() = runTest {
        // GIVEN
        val givenMail = "mock@gmail.test"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personCollectionMock = mockk<CollectionReference>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        val personQuery = mockk<Query>()
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns personCollectionMock
        every { personCollectionMock.whereEqualTo(FirebaseFirestoreFields.USER_ID, givenMail) } returns personQuery
        every { personQuery.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = personRepository.getAllPersonsByUserId(givenMail).observe(this)
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
    fun getAllPersonsByUserIdSuccessTest() = runTest {
        // GIVEN
        val documentId = "documentId"
        val givenId = "xZ04EdEgg"
        val givenPerson = provideRandomPerson().copy(userId = givenId)
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personCollectionMock = mockk<CollectionReference>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        val personQuery = mockk<Query>()
        val personQuerySnapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }

        every { firebaseFirestore.collection(any()) } returns personCollectionMock
        every { personCollectionMock.whereEqualTo(FirebaseFirestoreFields.USER_ID, givenId) } returns personQuery
        every { personQuerySnapshot.documents  } returns listOf(documentSnapshot)
        every { documentSnapshot.id } returns documentId
        every { documentSnapshot.toObject(Person::class.java) } returns givenPerson

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { personQuery.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(personQuerySnapshot, null)
            registration
        }

        // WHEN
        val flow = personRepository.getAllPersonsByUserId(givenId).observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Person>>(),
            Result.Success(listOf(givenPerson.copy(id = documentId)))
        )

        flow.finish()
    }

    // --------------
    // SUBMIT: PERSON
    // --------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitPersonFailureTest() = runTest {
        // GIVEN
        val givenPerson = Person()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore.collection(FirebaseFirestoreCollections.PERSONS)
                .add(any())
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            personRepository.submitPerson(givenPerson)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitPersonSuccessTest() = runTest {
        // GIVEN
        val givenPerson = Person()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
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
            personRepository.submitPerson(givenPerson)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        TestCase.assertFalse(exceptionThrown)
    }

    // --------------
    // UPDATE: PERSON
    // --------------

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updatePersonFailureTest() = runTest {
        // GIVEN
        val givenPerson = Person()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.PERSONS)
                .document(any())
                .update(FirebaseFirestoreFields.VALIDATED_MODULES, givenPerson.validatedModules)
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<Void>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            personRepository.updatePerson(givenPerson)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updatePersonSuccessTest() = runTest {
        // GIVEN
        val givenPerson = Person()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val personRepository = FirebaseFirestorePersonsImpl(firebaseFirestore)
        // Mocking the successful scenario
        val onSuccessListenerSlot = slot<OnSuccessListener<Void>>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.PERSONS)
                .document(any())
                .update(FirebaseFirestoreFields.VALIDATED_MODULES, givenPerson.validatedModules)
                .addOnSuccessListener(capture(onSuccessListenerSlot))
                .addOnFailureListener(any())
        } answers {
            onSuccessListenerSlot.captured.onSuccess(mockk(relaxed = true))
            mockk<Task<Void>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            personRepository.updatePerson(givenPerson)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        TestCase.assertFalse(exceptionThrown)
    }

}