package com.oxymium.si2gassistant.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreSuggestionsImpl
import com.oxymium.si2gassistant.domain.entities.mock.provideRandomSuggestion
import io.mockk.MockK
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class SuggestionRepositoryTest {
}