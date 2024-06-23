package com.romzc.bachesapp
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreClass(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("SETTINGS")
        val USER_ID_KEY = intPreferencesKey("user_id")
    }

    val getId: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: -1
        }

    suspend fun clearId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }

    suspend fun saveId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }
}