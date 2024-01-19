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
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreBugTicketsImpl
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreCollections
import com.oxymium.si2gassistant.domain.entities.FirebaseFirestoreFields
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.mock.provideRandomBugTicket
import com.oxymium.si2gassistant.utils.observe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

class BugTicketRepositoryTest {

    // --------------------
    // GET: ALL BUG TICKETS
    // --------------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllBugTicketsErrorTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketCollectionMock = mockk<CollectionReference>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns bugTicketCollectionMock
        every { bugTicketCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = bugTicketRepository.getAllBugTickets().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<BugTicket>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllBugTicketsSuccessTest() = runTest {
        // GIVEN
        val bugTicket = provideRandomBugTicket()
        val documentId = "0EzwPPa015z"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketCollectionMock = mockk<CollectionReference>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val snapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        every { snapshot.documents } returns listOf(documentSnapshot)
        every { documentSnapshot.id } returns documentId
        every { documentSnapshot.toObject<BugTicket>(any()) } returns bugTicket

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns bugTicketCollectionMock
        every { bugTicketCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(snapshot, null)
            registration
        }

        // WHEN
        val flow = bugTicketRepository.getAllBugTickets().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<BugTicket>>(),
            Result.Success(listOf(bugTicket.copy(id = documentId)))
        )

        flow.finish()

    }

    // -------------------------------
    // GET: ALL BUG TICKETS BY USER ID
    // -------------------------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getBugTicketsByUserFailureTest() = runTest {
        // GIVEN
        val givenMail = "mock@gmail.test"
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketCollectionMock = mockk<CollectionReference>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        val bugTicketQuery = mockk<Query>()
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns bugTicketCollectionMock
        every { bugTicketCollectionMock.whereEqualTo(FirebaseFirestoreFields.SUBMITTED_BY, givenMail) } returns bugTicketQuery
        every { bugTicketQuery.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = bugTicketRepository.getBugTicketsByUser(givenMail).observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<BugTicket>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getBugTicketsByUserSuccessTest() = runTest {
        // GIVEN
        val documentId = "documentId"
        val givenMail = "mock@gmail.test"
        val givenTicket = provideRandomBugTicket().copy(submittedBy = givenMail)
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketCollectionMock = mockk<CollectionReference>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        val bugTicketQuery = mockk<Query>()
        val bugTicketQuerySnapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }

        every { firebaseFirestore.collection(any()) } returns bugTicketCollectionMock
        every { bugTicketCollectionMock.whereEqualTo(FirebaseFirestoreFields.SUBMITTED_BY, givenMail) } returns bugTicketQuery
        every { bugTicketQuerySnapshot.documents  } returns listOf(documentSnapshot)
        every { documentSnapshot.id } returns documentId
        every { documentSnapshot.toObject(BugTicket::class.java) } returns givenTicket

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { bugTicketQuery.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(bugTicketQuerySnapshot, null)
            registration
        }

        // WHEN
        val flow = bugTicketRepository.getBugTicketsByUser(givenMail).observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<BugTicket>>(),
            Result.Success(listOf(givenTicket.copy(id = documentId)))
        )

        flow.finish()
    }

    // ------------------
    // SUBMIT: BUG TICKET
    // ------------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitBugTicketFailureTest() = runTest {
        // GIVEN
        val givenBugTicket = provideRandomBugTicket()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore.collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .add(any())
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            bugTicketRepository.submitBugTicket(givenBugTicket)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun submitBugTicketSuccessTest() = runTest {
        // GIVEN
        val givenBugTicket = provideRandomBugTicket()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        // Mocking the successful scenario
        val onSuccessListenerSlot = slot<OnSuccessListener<DocumentReference>>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .add(any())
                .addOnSuccessListener(capture(onSuccessListenerSlot))
                .addOnFailureListener(any())
        } answers {
            onSuccessListenerSlot.captured.onSuccess(mockk(relaxed = true))
            mockk<Task<DocumentReference>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            bugTicketRepository.submitBugTicket(givenBugTicket)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertFalse(exceptionThrown)
    }

    // -------------------
    // BUG TICKETS: UPDATE
    // -------------------
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateBugTicketFailureTest() = runTest {
        // GIVEN
        val givenBugTicket = provideRandomBugTicket()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        // Mocking the failure scenario
        val onFailureListenerSlot = slot<OnFailureListener>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .document(any())
                .update(
                    FirebaseFirestoreFields.RESOLVED, givenBugTicket.isResolved,
                    FirebaseFirestoreFields.RESOLVED_COMMENT, givenBugTicket.resolvedComment,
                    FirebaseFirestoreFields.RESOLVED_DATE, givenBugTicket.resolvedDate
                )
                .addOnSuccessListener(any())
                .addOnFailureListener(capture(onFailureListenerSlot))
        } answers {
            onFailureListenerSlot.captured.onFailure(mockk(relaxed = true))
            mockk<Task<Void>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            bugTicketRepository.updateBugTicket(givenBugTicket)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertTrue(exceptionThrown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateBugTicketSuccessTest() = runTest {
        // GIVEN
        val givenBugTicket = provideRandomBugTicket()
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val bugTicketRepository = FirebaseFirestoreBugTicketsImpl(firebaseFirestore)
        // Mocking the successful scenario
        val onSuccessListenerSlot = slot<OnSuccessListener<Void>>()
        coEvery {
            firebaseFirestore
                .collection(FirebaseFirestoreCollections.BUG_TICKETS)
                .document(any())
                .update(
                    FirebaseFirestoreFields.RESOLVED, givenBugTicket.isResolved,
                    FirebaseFirestoreFields.RESOLVED_COMMENT, givenBugTicket.resolvedComment,
                    FirebaseFirestoreFields.RESOLVED_DATE, givenBugTicket.resolvedDate
                )
                .addOnSuccessListener(capture(onSuccessListenerSlot))
                .addOnFailureListener(any())
        } answers {
            onSuccessListenerSlot.captured.onSuccess(mockk(relaxed = true))
            mockk<Task<Void>>(relaxed = true)
        }

        // WHEN
        val exceptionThrown = try {
            bugTicketRepository.updateBugTicket(givenBugTicket)
            false
        } catch (e: Exception) {
            true
        }

        // THEN
        assertFalse(exceptionThrown)
    }

}