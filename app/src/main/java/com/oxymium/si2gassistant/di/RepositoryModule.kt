package com.oxymium.si2gassistant.di

import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.data.repository.FirebaseAuthImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreAcademiesImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreAnnouncementsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreBugTicketsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreModuleImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestorePersonsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreSuggestionsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreUserImpl
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import com.oxymium.si2gassistant.domain.repository.AnnouncementRepository
import com.oxymium.si2gassistant.domain.repository.AuthRepository
import com.oxymium.si2gassistant.domain.repository.ModuleRepository
import com.oxymium.si2gassistant.domain.repository.PersonRepository
import com.oxymium.si2gassistant.domain.repository.SuggestionRepository
import com.oxymium.si2gassistant.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModules = module {

    // REPO: Auth
    single<AuthRepository> { FirebaseAuthImpl(firebaseAuth = get() ) }

    // REPO: User
    single<UserRepository> { FirebaseFirestoreUserImpl(firebaseFirestore = get() ) }

    // REPO: Person
    single<PersonRepository> { FirebaseFirestorePersonsImpl(firebaseFirestore = get() ) }

    // REPO: Module
    single<ModuleRepository> { FirebaseFirestoreModuleImpl(firebaseFirestore = get() ) }

    // REPO: Academy
    single<AcademyRepository> { FirebaseFirestoreAcademiesImpl(firebaseFirestore = get() ) }

    // REPO: Bug Ticket
    single<BugTicketRepository> { FirebaseFirestoreBugTicketsImpl(firebaseFirestore = get() ) }

    // REPO: Suggestion
    single<SuggestionRepository> { FirebaseFirestoreSuggestionsImpl(firebaseFirestore = get() ) }

    // REPO: Announcement
    single<AnnouncementRepository> { FirebaseFirestoreAnnouncementsImpl(firebaseFirestore = get() ) }

}