package com.oxymium.si2gassistant.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreAcademiesImpl
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.Result
import com.oxymium.si2gassistant.domain.entities.mock.ALL_ACADEMIES
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class AcademyRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllAcademiesTest() = runTest {
        // GIVEN
        val firebaseFirestore = mockk<FirebaseFirestore>()
        val givenAcademies = ALL_ACADEMIES
        val givenResult = Result.Success(givenAcademies)
        val academyRepository = FirebaseFirestoreAcademiesImpl(firebaseFirestore)

        every { firebaseFirestore.collection(any()).get() } returns mockk(relaxed = true)
        coEvery { academyRepository.getAllAcademies() } returns flowOf(givenResult)

        // WHEN
        val result = academyRepository.getAllAcademies().first()
        var academies: List<Academy>? = null
        when (result) {
            is Result.Failed -> println("failed")
            is Result.Loading -> println("loading")
            is Result.Success -> academies = result.data
        }

        // THEN
        assertEquals(givenAcademies, academies)
    }

}