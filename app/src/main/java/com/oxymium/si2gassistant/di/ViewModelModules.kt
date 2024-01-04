package com.oxymium.si2gassistant.di

import com.oxymium.si2gassistant.ui.NavigationViewModel
import com.oxymium.si2gassistant.ui.scenes.buglist.BugTicketViewModel
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsViewModel
import com.oxymium.si2gassistant.ui.scenes.splash.SplashViewModel
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsViewModel
import com.oxymium.si2gassistant.ui.scenes.persons.PersonListViewModel
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugViewModel
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonViewModel
import com.oxymium.si2gassistant.ui.scenes.submitsuggestion.SubmitSuggestionViewModel
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    // VM: Navigation
    viewModel { NavigationViewModel() }

    // VM: FirebaseUser
    viewModel { SplashViewModel( get(), get() ) }

    // WM: Greetings
    viewModel { GreetingsViewModel( get(), get(), get(), get() ) }

    // WM : Metrics
    viewModel { MetricsViewModel( get(), get(), get(), get(), get(), get() ) }

    // VM: BugTickets
    viewModel { BugTicketViewModel( get() ) }

    // VM: SubmitPerson
    viewModel { SubmitPersonViewModel( get() ) }

    // VM: ReportBugTicket
    viewModel { ReportBugViewModel( get() ) }

    // VM: Persons
    viewModel { PersonListViewModel( get() ) }

    // VM: SubmitSuggestion
    viewModel { SubmitSuggestionViewModel( get() ) }

    // VM: Suggestion
    viewModel { SuggestionsViewModel( get() ) }

}