package com.LibBib.spewnik.data.options

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.LibBib.spewnik.di.ApplicationScope
import com.LibBib.spewnik.domain.options.Options
import com.LibBib.spewnik.domain.options.OptionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@ApplicationScope
class OptionsRepositoryImpl @Inject constructor(
    private val application: Application,
) : OptionsRepository {


    override fun getOptions(): Options {
        val chordsVisibleKey = booleanPreferencesKey(CHORDS_VISIBLE_KEY)
        val chordsVisibleKeyFlow: Flow<Boolean> = application.dataStore.data.map { preferences ->
            preferences[chordsVisibleKey] ?: true
        }


        return TODO("Provide the return value")
    }


    companion object {
        private const val CHORDS_VISIBLE_KEY = "chords_visible_key"
    }
}