package com.belizwp.myweather.domain

import com.belizwp.myweather.data.repository.WeatherRepository
import com.belizwp.myweather.domain.usecase.GetWeatherByCityNameUseCase
import com.belizwp.myweather.fake.FakeWeatherRepository
import com.belizwp.myweather.rule.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherByCityNameUseCaseTest {

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    @Test
    fun getWeatherByCityNameUseCase_invoke_verifyData() =
        runTest {
            val mockWeatherRepository: WeatherRepository = FakeWeatherRepository()
            val getWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(mockWeatherRepository)
            val weather = getWeatherByCityNameUseCase("London")

            assert(weather.cityName == "London")
        }
}
