package com.jhonjto.domain.usecase

import com.jhonjto.domain.City
import com.jhonjto.domain.repository.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(): List<City> = repository.fetchCities()
}
