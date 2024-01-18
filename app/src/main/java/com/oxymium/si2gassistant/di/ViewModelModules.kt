package com.oxymium.si2gassistant.di

import com.oxymium.si2gassistant.ui.AppViewModel
import com.oxymium.si2gassistant.ui.scenes.bugtickets.BugTicketsViewModel
import com.oxymium.si2gassistant.ui.scenes.chat.ChatViewModel
import com.oxymium.si2gassistant.ui.scenes.greetings.GreetingsViewModel
import com.oxymium.si2gassistant.ui.scenes.splash.SplashViewModel
import com.oxymium.si2gassistant.ui.scenes.metrics.MetricsViewModel
import com.oxymium.si2gassistant.ui.scenes.persons.PersonsViewModel
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugViewModel
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonViewModel
import com.oxymium.si2gassistant.ui.scenes.submitsuggestion.SubmitSuggestionViewModel
import com.oxymium.si2gassistant.ui.scenes.suggestions.SuggestionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    // VM: App
    viewModel { AppViewModel( get(), get(), get() ) }

    // VM: FirebaseUser
    viewModel { SplashViewModel( get() ) }

    // WM: Greetings
    viewModel { GreetingsViewModel( get(), get() ) }

    // WM : Metrics
    viewModel { MetricsViewModel( get(), get(), get(), get(), get(), get() ) }

    // VM: BugTickets
    viewModel { BugTicketsViewModel( get() ) }

    // VM: SubmitPerson
    viewModel { SubmitPersonViewModel( get() ) }

    // VM: ReportBugTicket
    viewModel { ReportBugViewModel( get() ) }

    // VM: Persons
    viewModel { PersonsViewModel( get() ) }

    // VM: SubmitSuggestion
    viewModel { SubmitSuggestionViewModel( get() ) }

    // VM: Suggestion
    viewModel { SuggestionsViewModel( get() ) }

    // VM: Chat
    viewModel { ChatViewModel( get() ) }

}