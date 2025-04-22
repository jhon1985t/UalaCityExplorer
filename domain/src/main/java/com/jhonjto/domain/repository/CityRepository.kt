package com.jhonjto.domain.repository

import com.jhonjto.domain.City

interface CityRepository {
    suspend fun fetchCities(): List<City>
}
