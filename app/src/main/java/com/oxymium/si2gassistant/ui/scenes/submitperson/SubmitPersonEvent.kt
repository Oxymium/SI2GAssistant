package com.oxymium.si2gassistant.ui.scenes.submitperson

import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.usecase.BugTicketListEvent

sealed interface SubmitPersonEvent {

    data object OnSubmitPersonModeButtonClicked: SubmitPersonEvent
    data object OnPersonsModeButtonClicked: SubmitPersonEvent

    data class OnPersonRoleChanged(val personRole: String): SubmitPersonEvent
    data class OnPersonFirstNameChanged(val personFirstName: String): SubmitPersonEvent
    data class OnPersonLastNameChanged(val personLastName: String): SubmitPersonEvent
    data object OnSubmitPersonButtonClicked: SubmitPersonEvent

    data class OnSelectedPerson(val person: Person): SubmitPersonEvent
    data object DismissPersonDetailsSheet: SubmitPersonEvent

    data class OnPersonModulesUpdateButtonClicked(val modules: String): SubmitPersonEvent

    data class OnPersonModulesSwitchToggle(val moduleId: Int, val isChecked: Boolean): SubmitPersonEvent

}