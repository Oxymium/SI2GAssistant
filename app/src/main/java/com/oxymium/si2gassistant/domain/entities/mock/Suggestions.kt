package com.oxymium.si2gassistant.domain.entities.mock

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.BugTicketCategory
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.domain.entities.Suggestion
import kotlin.random.Random

fun provideRandomSuggestion(): Suggestion {

    val randomAmountOfBodyWords = (30..100).random()

    return Suggestion(
        "",
        LoremIpsum(5).values.joinToString(""),
        LoremIpsum(randomAmountOfBodyWords).values.joinToString(""),
        "",
        ALL_ACADEMIES.random().shortTitle,
        (dateInMillis..secondDateInMillis).random()
    )

}