package com.jhonjto.ualacityexplorer

import com.jhonjto.data.model.datasource.PreferencesGateway
import com.jhonjto.domain.City
import com.jhonjto.domain.usecase.GetCitiesUseCase
import com.jhonjto.domain.usecase.PreprocessCityIndexUseCase
import com.jhonjto.domain.usecase.SearchCitiesUseCase
import com.jhonjto.ualacityexplorer.presentation.citylistscreen.CityViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CityViewModel

    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private lateinit var searchCitiesUseCase: SearchCitiesUseCase
    private lateinit var preprocessCityIndexUseCase: PreprocessCityIndexUseCase
    private lateinit var prefs: PreferencesGateway

    private val fakeCities = listOf(
        City(1, "Alabama", "US", 1.0, 1.0),
        City(2, "Albuquerque", "US", 2.0, 2.0),
        City(3, "Sydney", "AU", 3.0, 3.0),
    )

    private val fakeCityIndex = mapOf(
        'a' to listOf(fakeCities[0], fakeCities[1]),
        's' to listOf(fakeCities[2])
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)

        getCitiesUseCase = mockk()
        searchCitiesUseCase = mockk()
        preprocessCityIndexUseCase = mockk()
        prefs = FakePreferenceManager()

        coEvery { getCitiesUseCase.invoke() } returns fakeCities
        every { preprocessCityIndexUseCase.buildIndex(fakeCities) } returns fakeCityIndex
        every { searchCitiesUseCase.invoke(fakeCityIndex, any()) } answers {
            val query = secondArg<String>()
            fakeCityIndex.values.flatten()
                .filter { it.name.startsWith(query, ignoreCase = true) }
                .sortedBy { it.name.lowercase() }
        }

        viewModel = CityViewModel(
            getCitiesUseCase = getCitiesUseCase,
            searchCitiesUseCase = searchCitiesUseCase,
            preprocessCityIndexUseCase = preprocessCityIndexUseCase,
            prefs = prefs
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onQueryChange filters cities by prefix`() = runTest {
        viewModel.onQueryChange("Al")
        val result = viewModel.filteredCities
        assertEquals(2, result.size)
        assertTrue(result.any { it.name == "Alabama" })
        assertTrue(result.any { it.name == "Albuquerque" })
    }

    @Test
    fun `toggleFavorite adds and removes correctly`() = runTest {
        viewModel.onToggleFavorite(1)
        advanceUntilIdle()
        assertTrue((prefs.favoriteIds as StateFlow<Set<Int>>).value.contains(1))

        viewModel.onToggleFavorite(1)
        advanceUntilIdle()
        assertFalse((prefs.favoriteIds as StateFlow<Set<Int>>).value.contains(1))
    }

    @Test
    fun `onlyFavorites shows only favorite cities`() = runTest {
        viewModel.onQueryChange("Al")
        viewModel.onToggleFavorite(2)
        advanceUntilIdle()

        viewModel.toggleOnlyFavorites()
        val filtered = viewModel.filteredCities
        assertEquals(1, filtered.size)
        assertEquals(2, filtered.first().id)
    }
}
