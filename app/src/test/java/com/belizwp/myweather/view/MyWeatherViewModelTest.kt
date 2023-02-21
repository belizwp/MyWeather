package com.belizwp.myweather.view

import com.belizwp.myweather.domain.model.Weather
import com.belizwp.myweather.domain.usecase.GetWeatherByCityNameUseCase
import com.belizwp.myweather.fake.FakeWeatherRepository
import com.belizwp.myweather.rule.TestDispatcherRule
import com.belizwp.myweather.ui.MyWeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MyWeatherViewModelTest {

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    private val mockWeatherRepository = FakeWeatherRepository()
    private val mockGetWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(mockWeatherRepository)
    private val viewModel = MyWeatherViewModel(mockGetWeatherByCityNameUseCase)

    @Test
    fun myWeatherViewModel_initialization_verifyDefaultUiState() = runTest {
        val uiState = viewModel.uiState.value

        assertTrue(uiState.query == "")
        assertTrue(uiState.isQueryValid)
        assertFalse(uiState.isLoading)
        assertFalse(uiState.isRefreshing)
        assertEquals(uiState.weather, Weather())
    }

    @Test
    fun myWeatherViewModel_updateQuery_verifyQueryUpdated() = runTest {
        val uiState = viewModel.uiState.value
        assertEquals(uiState.query, "")

        viewModel.updateQuery("London")

        val uiState2 = viewModel.uiState.value
        assertEquals(uiState2.query, "London")
    }

    @Test
    fun myWeatherViewModel_search_verifySearchState() = runTest {
        viewModel.updateQuery("London")
        viewModel.search()

        val uiState = viewModel.uiState.value

        assertNotEquals(uiState.weather.cityName, "")
    }
}
