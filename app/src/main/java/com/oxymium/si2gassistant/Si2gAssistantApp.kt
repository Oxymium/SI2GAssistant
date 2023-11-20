package com.oxymium.si2gassistant

import android.app.Application
import androidx.core.view.WindowCompat
import com.google.firebase.FirebaseApp
import com.oxymium.si2gassistant.di.firebaseModules
import com.oxymium.si2gassistant.di.repositoryModules
import com.oxymium.si2gassistant.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Si2gAssistantApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // Start injection with KOIN
        startKoin {
            androidContext(this@Si2gAssistantApp)
            modules(firebaseModules)
            modules(repositoryModules)
            modules(viewModelModules)
        }

        FirebaseApp.initializeApp(this)

    }
}