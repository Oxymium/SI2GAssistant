package com.oxymium.si2gassistant.ui.scenes.metrics

import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Suggestion
import com.oxymium.si2gassistant.domain.entities.User

data class MetricsState(
    val bugTicketList: List<BugTicket>? = null,
    val academies: List<Academy>? = null,
    val modules: List<Module>? = null,
    val users: List<User>? = null,
    val persons: List<Person>? = null,
    val suggestions: List<Suggestion>? = null
)