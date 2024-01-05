package com.oxymium.si2gassistant.ui.scenes.submitperson

import com.oxymium.si2gassistant.domain.entities.Person

sealed interface SubmitPersonEvent {
    data object OnSubmitPersonModeButtonClick: SubmitPersonEvent
    data object OnPersonsModeButtonClick: SubmitPersonEvent
    data class OnPersonRoleChange(val personRole: String): SubmitPersonEvent
    data class OnPersonFirstNameChange(val personFirstName: String): SubmitPersonEvent
    data class OnPersonLastNameChange(val personLastName: String): SubmitPersonEvent
    data object OnSubmitPersonButtonClick: SubmitPersonEvent
    data class OnPersonSelect(val person: Person): SubmitPersonEvent
    data object DismissPersonDetailsSheet: SubmitPersonEvent
    data class OnPersonModulesSwitchToggle(val moduleId: Int, val isChecked: Boolean): SubmitPersonEvent
}