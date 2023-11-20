package com.oxymium.si2gassistant.di

import com.oxymium.si2gassistant.domain.entities.BugTicket
import com.oxymium.si2gassistant.ui.NavigationViewModel
import com.oxymium.si2gassistant.ui.scenes.buglist.BugTicketViewModel
import com.oxymium.si2gassistant.ui.scenes.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    // VM: Navigation
    viewModel { NavigationViewModel() }

    // VM: FirebaseUser
    viewModel { LoginViewModel( get() ) }

    // VM: BugTickets
    viewModel { BugTicketViewModel( get() ) }
}