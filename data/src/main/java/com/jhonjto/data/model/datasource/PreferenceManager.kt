package com.jhonjto.data.model.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object DataStoreKeys {
    val FAVORITE_IDS = stringSetPreferencesKey("favorite_city_ids")
}

class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
): PreferencesGateway {
    private val dataStore = context.dataStore

    override val favoriteIds: Flow<Set<Int>> = dataStore.data
        .map { prefs ->
            prefs[DataStoreKeys.FAVORITE_IDS]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        }

    override suspend fun toggleFavorite(id: Int) {
        dataStore.edit { prefs ->
            val current = prefs[DataStoreKeys.FAVORITE_IDS]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(id.toString())) current.remove(id.toString())
            else current.add(id.toString())
            prefs[DataStoreKeys.FAVORITE_IDS] = current
        }
    }
}
