package com.jhonjto.domain.usecase

import com.jhonjto.domain.City
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor() {

    operator fun invoke(
        index: Map<Char, List<City>>,
        query: String
    ): List<City> {
        if (query.isBlank()) return emptyList()
        val initial = query.first().lowercaseChar()
        return index[initial]
            ?.filter { it.name.startsWith(query, ignoreCase = true) }
            ?.sortedBy { it.name.lowercase() }
            ?: emptyList()
    }
}
