package com.jhonjto.ualacityexplorer

import com.jhonjto.data.model.datasource.PreferencesGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePreferenceManager : PreferencesGateway {
    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())

    override val favoriteIds: Flow<Set<Int>> get() = _favorites

    override suspend fun toggleFavorite(id: Int) {
        val current = _favorites.value.toMutableSet()
        if (current.contains(id)) current.remove(id)
        else current.add(id)
        _favorites.value = current
    }
}

