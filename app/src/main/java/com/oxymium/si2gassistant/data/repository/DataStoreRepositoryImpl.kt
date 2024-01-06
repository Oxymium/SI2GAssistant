package com.oxymium.si2gassistant.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.oxymium.si2gassistant.domain.entities.Login
import com.oxymium.si2gassistant.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first

const val DATASTORE_LOGIN = "data_login"
const val DATASTORE_KEY_MAIL = "MAIL"
const val DATASTORE_KEY_PASSWORD = "PASSWORD"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_LOGIN)
class DataStoreRepositoryImpl(
    private val context: Context
): DataStoreRepository {

    // SAVE
    override suspend fun putLogin(login: Login) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(DATASTORE_KEY_MAIL)] = login.mail
            preferences[stringPreferencesKey(DATASTORE_KEY_PASSWORD)] = login.password
        }
    }

    // LOAD
    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}