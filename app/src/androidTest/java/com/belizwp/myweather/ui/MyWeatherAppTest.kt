package com.belizwp.myweather.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.belizwp.myweather.domain.model.Weather
import com.belizwp.myweather.ui.screen.SearchScreen
import com.belizwp.myweather.ui.screen.WeatherScreen
import org.junit.Rule
import org.junit.Test

class MyWeatherAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeWeather = Weather(
        cityName = "London",
        country = "UK",
        temperature = "20",
        weatherIconUrl = "https://openweathermap.org/img/w/01d.png",
        humidity = "50",
        pressure = "1000",
        uvIndex = "5",
        wind = "10",
        lat = 51.5085,
        lon = -0.1257,
    )

    private val fakeMyWeatherUiState = MyWeatherUiState(query = "London", weather = fakeWeather)

    @Test
    fun searchScreen_verifyContent() {

        // When SearchScreen is loaded
        composeTestRule.setContent {
            SearchScreen(
                uiState = fakeMyWeatherUiState,
            )
        }

        // Then query must displayed on the screen.
        composeTestRule.onNodeWithText(fakeMyWeatherUiState.query).assertIsDisplayed()
    }

    @Test
    fun weatherScreen_verifyContent() {

        // When WeatherScreen is loaded
        composeTestRule.setContent {
            WeatherScreen(
                uiState = fakeMyWeatherUiState,
            )
        }

        // Then all weather data must displayed on the screen.
        composeTestRule.onNodeWithText(fakeWeather.cityName).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeWeather.country).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeWeather.temperature).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeWeather.humidity).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeWeather.pressure).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeWeather.uvIndex).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeWeather.wind).assertIsDisplayed()
    }
}
