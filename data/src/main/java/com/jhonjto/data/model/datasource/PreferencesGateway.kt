package com.jhonjto.data.model.datasource

import kotlinx.coroutines.flow.Flow

interface PreferencesGateway {
    val favoriteIds: Flow<Set<Int>>
    suspend fun toggleFavorite(id: Int)
}
