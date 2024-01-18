package com.oxymium.si2gassistant.domain.repository

import com.google.common.truth.Truth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreUsersImpl
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.User
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

class UserRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsersErrorTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val userCollectionMock = mockk<CollectionReference>()
        val userRepository = FirebaseFirestoreUsersImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns userCollectionMock
        every { userCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = userRepository.getAllUsers().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<User>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsersSuccessTest() = runTest {
        // GIVEN
        val user = User("001", "test@gmail.test", "Academy", "John", "Doe", false)
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val userCollectionMock = mockk<CollectionReference>()
        val userRepository = FirebaseFirestoreUsersImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val snapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        every { snapshot.documents } returns listOf(documentSnapshot)
        every { documentSnapshot.toObject<User>(any()) } returns user

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns userCollectionMock
        every { userCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(snapshot, null)
            registration
        }

        // WHEN
        val flow = userRepository.getAllUsers().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<User>>(),
            Result.Success(listOf(user))
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUserByUidFailureTest() = runTest {
        // GIVEN
        val givenUid = "ixOZa46Ew"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val userCollectionMock = mockk<CollectionReference>()
        val userRepository = FirebaseFirestoreUsersImpl(firebaseFirestore)
        val userDocumentReference = mockk<DocumentReference>()
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<DocumentSnapshot>>()
        every { firebaseFirestore.collection(any()) } returns userCollectionMock
        every { userCollectionMock.document(givenUid) } returns userDocumentReference
        every { userDocumentReference.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = userRepository.getUserByUid(givenUid).observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<User>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUserByUidSuccessTest() = runTest {
        // GIVEN
        val givenUid = "xEA496tXo01"
        val givenUser = User().apply { id = givenUid }
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val userCollectionMock = mockk<CollectionReference>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        val userRepository = FirebaseFirestoreUsersImpl(firebaseFirestore)
        val userDocumentReference = mockk<DocumentReference>()
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }

        val callbackSlot = slot<EventListener<DocumentSnapshot>>()
        every { firebaseFirestore.collection(any()) } returns userCollectionMock
        every { userCollectionMock.document(givenUid) } returns userDocumentReference
        every { documentSnapshot.toObject(User::class.java) } returns givenUser
        every { userDocumentReference.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(documentSnapshot, null)
            registration
        }

        // WHEN
        val flow = userRepository.getUserByUid(givenUid).observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<User>(),
            Result.Success(givenUser)
        )

        flow.finish()
    }


}