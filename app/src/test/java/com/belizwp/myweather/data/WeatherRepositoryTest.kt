package com.belizwp.myweather.data

import com.belizwp.myweather.data.repository.WeatherRepository
import com.belizwp.myweather.data.repository.WeatherRepositoryImpl
import com.belizwp.myweather.data.service.WeatherStackApiService
import com.belizwp.myweather.fake.FakeWeatherStackResponse
import com.belizwp.myweather.rule.TestDispatcherRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    @Mock
    private lateinit var mockWeatherStackApiService: WeatherStackApiService

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun weatherRepository_getWeatherByCityName_withSuccessData() =
        runTest {
            val mockWeatherStackResponse = FakeWeatherStackResponse.successResponse
            val weatherRepository: WeatherRepository =
                WeatherRepositoryImpl(mockWeatherStackApiService)

            Mockito.`when`(
                mockWeatherStackApiService.getCurrentWeather(any(), eq("London"))
            ).thenReturn(mockWeatherStackResponse)

            val weatherData = weatherRepository.getWeatherByCityName("London")

            assert(weatherData.cityName == "London")
        }

    @Test(expected = Exception::class)
    fun weatherRepository_getWeatherByCityName_withErrorData() =
        runTest {
            val mockWeatherStackResponse = FakeWeatherStackResponse.errorResponse
            val weatherRepository: WeatherRepository =
                WeatherRepositoryImpl(mockWeatherStackApiService)

            Mockito.`when`(
                mockWeatherStackApiService.getCurrentWeather(any(), eq("London"))
            ).thenReturn(mockWeatherStackResponse)

            weatherRepository.getWeatherByCityName("London")
        }
}
