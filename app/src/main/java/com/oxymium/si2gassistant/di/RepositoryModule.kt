package com.oxymium.si2gassistant.di

import com.oxymium.si2gassistant.domain.repository.BugTicketRepository
import com.oxymium.si2gassistant.data.repository.FirebaseAuthUserImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreAcademiesImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreBugTicketsImpl
import com.oxymium.si2gassistant.data.repository.FirebaseFirestoreModuleImpl
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import com.oxymium.si2gassistant.domain.repository.ModuleRepository
import com.oxymium.si2gassistant.domain.repository.FirebaseUserRepository
import org.koin.dsl.module

val repositoryModules = module {

    // REPO: FirebaseUser
    single<FirebaseUserRepository> { FirebaseAuthUserImpl(firebaseAuth = get()) }

    // REPO: Module
    single<ModuleRepository> { FirebaseFirestoreModuleImpl(firebaseFirestore = get()) }

    // REPO: Academy
    single<AcademyRepository> { FirebaseFirestoreAcademiesImpl(firebaseFirestore = get()) }

    // REPO: Bug Ticket
    single<BugTicketRepository> { FirebaseFirestoreBugTicketsImpl(firebaseFirestore = get()) }

}