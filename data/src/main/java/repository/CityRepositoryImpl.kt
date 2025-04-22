package repository

import com.jhonjto.data.model.datasource.CityApi
import com.jhonjto.data.model.toDomain
import com.jhonjto.domain.City
import com.jhonjto.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val api: CityApi
) : CityRepository {
    override suspend fun fetchCities(): List<City> {
        return api.getCities().map { it.toDomain() }
    }
}
