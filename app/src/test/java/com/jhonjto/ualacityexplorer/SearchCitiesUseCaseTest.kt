package com.jhonjto.ualacityexplorer

import com.jhonjto.domain.City
import com.jhonjto.domain.usecase.SearchCitiesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCitiesUseCaseTest {

    private val useCase = SearchCitiesUseCase()

    private val cities = listOf(
        City(1, "Alabama", "US", 0.0, 0.0),
        City(2, "Albuquerque", "US", 0.0, 0.0),
        City(3, "Anaheim", "US", 0.0, 0.0),
        City(4, "Arizona", "US", 0.0, 0.0),
        City(5, "Sydney", "AU", 0.0, 0.0)
    )

    private val cityIndex = mapOf(
        'a' to cities
    )

    @Test
    fun `filter with prefix Al returns Alabama and Albuquerque`() {
        val result = useCase(cityIndex, "Al")
        assertEquals(2, result.size)
        assertTrue(result.any { it.name == "Alabama" })
        assertTrue(result.any { it.name == "Albuquerque" })
    }

    @Test
    fun `filter with lowercase s returns Sydney`() {
        val sIndex = mapOf('s' to listOf(cities[4]))
        val result = useCase(sIndex, "s")
        assertEquals(1, result.size)
        assertEquals("Sydney", result.first().name)
    }

    @Test
    fun `filter with invalid prefix returns empty`() {
        val result = useCase(cityIndex, "Z")
        assertTrue(result.isEmpty())
    }
}
