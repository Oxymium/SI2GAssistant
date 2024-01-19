package com.oxymium.si2gassistant.domain.repository

import com.google.common.truth.Truth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreModulesImpl
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Result
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

class ModuleRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllModulesErrorTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val moduleCollectionMock = mockk<CollectionReference>()
        val moduleRepository = FirebaseFirestoreModulesImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val exception = mockk<FirebaseFirestoreException> {
            every { message } returns "exception"
        }

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns moduleCollectionMock
        every { moduleCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(null, exception)
            registration
        }

        // WHEN
        val flow = moduleRepository.getAllModules().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Module>>(),
            Result.Failed<String>("exception")
        )

        flow.finish()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllModulesSuccessTest() = runTest {
        // GIVEN
        val module = Module("001", "Module 1", "Module content")
        val moduleList = listOf(module)
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val moduleCollectionMock = mockk<CollectionReference>()
        val moduleRepository = FirebaseFirestoreModulesImpl(firebaseFirestore)
        val registration = mockk<ListenerRegistration> {
            every { remove() } just Runs
        }
        val snapshot = mockk<QuerySnapshot>()
        val documentSnapshot = mockk<DocumentSnapshot>()
        every { snapshot.documents } returns listOf(documentSnapshot)
        every { documentSnapshot.toObject<Module>(any()) } returns module

        val callbackSlot = slot<EventListener<QuerySnapshot>>()
        every { firebaseFirestore.collection(any()) } returns moduleCollectionMock
        every { moduleCollectionMock.addSnapshotListener(capture(callbackSlot)) } answers {
            callbackSlot.captured.onEvent(snapshot, null)
            registration
        }

        // WHEN
        val flow = moduleRepository.getAllModules().observe(this)
        advanceUntilIdle()

        // THEN
        Truth.assertThat(flow.values).containsExactly(
            Result.Loading<List<Module>>(),
            Result.Success(moduleList)
        )

        flow.finish()

    }

}