package com.oxymium.si2gassistant.domain.states

import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.entities.User

data class MetricsState(
    val bugTickets: List<BugTicket>? = null,
    val academies: List<Academy>? = null,
    val modules: List<Module>? = null,
    val users: List<User>? = null,
    val persons: List<Person>? = null,
    val suggestions: List<Suggestion>? = null,
    val isOverallMetricsScreen: Boolean = true, // default
    val isBugTicketMetricsScreen: Boolean = false
)