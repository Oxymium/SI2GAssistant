package com.oxymium.si2gassistant.domain.states

import com.oxymium.si2gassistant.domain.entities.Announcement
import com.oxymium.si2gassistant.domain.entities.User

data class GreetingsState(
    val currentUser: User? = null,
    val announcements: List<Announcement> = emptyList(),
    val isAnnouncementsFailure: Boolean = false,
    val announcementsFailureMessage: String? = null,
    val isAnnouncementsLoading: Boolean = false,
    val isGreetingsMode: Boolean = true, // initial value
    val isTestingMode: Boolean = false
)