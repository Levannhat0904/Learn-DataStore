package net.braniumacademy.lesson12_2

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

const val DATA_PREFERENCES_NAME = "data_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_PREFERENCES_NAME)