package com.oxymium.si2gassistant.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val contextModules = module {
    single { androidContext() }
}