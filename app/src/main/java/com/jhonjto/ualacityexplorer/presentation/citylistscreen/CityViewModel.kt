package com.jhonjto.ualacityexplorer.presentation.citylistscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonjto.data.model.datasource.PreferencesGateway
import com.jhonjto.domain.City
import com.jhonjto.domain.usecase.GetCitiesUseCase
import com.jhonjto.domain.usecase.PreprocessCityIndexUseCase
import com.jhonjto.domain.usecase.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val preprocessCityIndexUseCase: PreprocessCityIndexUseCase,
    private val prefs: PreferencesGateway
) : ViewModel() {

    private var cityIndex: Map<Char, List<City>> = emptyMap()
    private var allCities: List<City> = emptyList()

    var filteredCities by mutableStateOf<List<City>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
        private set

    var favoriteIds by mutableStateOf(setOf<Int>())
        private set

    var onlyFavorites by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            allCities = getCitiesUseCase()
            cityIndex = preprocessCityIndexUseCase.buildIndex(allCities)
            prefs.favoriteIds.collect { ids ->
                favoriteIds = ids
                updateFilteredList()
            }
        }
    }

    fun onQueryChange(query: String) {
        searchQuery = query
        updateFilteredList()
    }

    fun onToggleFavorite(id: Int) {
        viewModelScope.launch {
            prefs.toggleFavorite(id)
        }
    }

    fun toggleOnlyFavorites() {
        onlyFavorites = !onlyFavorites
        updateFilteredList()
    }

    fun getCityById(id: Int): City? {
        return cityIndex.values.flatten().find { it.id == id }
    }

    private fun updateFilteredList() {
        val result = searchCitiesUseCase(cityIndex, searchQuery)
        filteredCities = if (onlyFavorites) result.filter { it.id in favoriteIds } else result
    }
}
