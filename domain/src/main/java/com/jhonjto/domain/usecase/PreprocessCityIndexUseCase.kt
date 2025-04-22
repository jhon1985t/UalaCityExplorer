package com.jhonjto.domain.usecase

import com.jhonjto.domain.City
import javax.inject.Inject

class PreprocessCityIndexUseCase @Inject constructor() {
    fun buildIndex(cities: List<City>): Map<Char, List<City>> {
        return cities.groupBy { it.name.first().lowercaseChar() }
    }
}
