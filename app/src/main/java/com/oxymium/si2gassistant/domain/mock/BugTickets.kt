package com.oxymium.si2gassistant.domain.mock

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.BugTicketCategory
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import kotlin.random.Random

// Jan 1st 2020
const val dateInMillis = 1577836800000L
// March 31st 2024
const val secondDateInMillis = 1679836800000L
fun provideRandomBugTicket(
    priority: BugTicketPriority = BugTicketPriority.entries.random(),
    isResolved: Boolean = Random.nextBoolean()
    ): BugTicket {

    return BugTicket(
        null,
        priority,
        BugTicketCategory.entries.random(),
        LoremIpsum(4).values.joinToString(""),
        LoremIpsum(10).values.joinToString(""),
        ALL_ACADEMIES.random().shortTitle,
        ALL_ACADEMIES.random().shortTitle + "@gmail.test",
        Random.nextLong(dateInMillis, secondDateInMillis),
        isResolved,
        if (isResolved) Random.nextLong(dateInMillis, secondDateInMillis) else null,
        if (isResolved) LoremIpsum(10).values.joinToString("") else null,
        )

}