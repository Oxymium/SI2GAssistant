package com.oxymium.si2gassistant.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

// Local firebase check
const val FIREBASE_EMULATOR = true
const val EMULATOR_IP = "10.0.2.2"
const val AUTH_EMULATOR_PORT = 9099
const val FIRESTORE_EMULATOR_PORT = 8080

val firebaseModules = module {

    // Firebase AUTH singleton
    single {
        Firebase.auth.apply {
            if (FIREBASE_EMULATOR) useEmulator(EMULATOR_IP, AUTH_EMULATOR_PORT)
        }}

    // Firebase FIRESTORE singleton
    single {
        Firebase.firestore.apply {
            if (FIREBASE_EMULATOR) useEmulator(EMULATOR_IP, FIRESTORE_EMULATOR_PORT)
        }}

}