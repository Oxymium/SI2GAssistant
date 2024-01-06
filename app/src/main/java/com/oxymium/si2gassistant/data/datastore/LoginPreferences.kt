package com.oxymium.si2gassistant.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")
    private val dataStore = context.dataStore

    suspend fun saveMailIntoDataStore(mail: String) {
        dataStore.edit {
            it[mailKey] = mail
        }
    }

    fun loadMailFromDataStore(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val mail = pref[mailKey] ?: ""
                mail
            }
    }

    companion object {
        val mailKey = stringPreferencesKey("MAIL_KEY")
    }

}
