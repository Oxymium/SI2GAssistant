package com.oxymium.si2gassistant.domain.repository

import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Message
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Suggestion

interface TestRepository {

    suspend fun submitAcademy(academy: Academy)
    suspend fun submitBugTicket(bugTicket: BugTicket)
    suspend fun submitModules(module: Module)
    suspend fun submitPerson(person: Person)
    suspend fun submitSuggestion(suggestion: Suggestion)
    suspend fun submitAnnouncement(announcement: Announcement)
    suspend fun submitMessage(message: Message)
}