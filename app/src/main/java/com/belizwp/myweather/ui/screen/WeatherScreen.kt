package com.belizwp.myweather.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.belizwp.myweather.R
import com.belizwp.myweather.domain.model.Weather
import com.belizwp.myweather.ui.theme.MyWeatherTheme
import com.belizwp.myweather.util.verticalGradientScrim

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weather: Weather = Weather(),
    onCityNameClicked: () -> Unit = {},
    navigateBack: () -> Unit = {},
    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    BackHandler() {
        navigateBack()
    }

    val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh)

    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalGradientScrim(
                            color = MaterialTheme.colors.primary.copy(alpha = 0.38f),
                            startYPercentage = 1f,
                            endYPercentage = 0f
                        )
                        .clickable { onCityNameClicked() }
                        .padding(32.dp)
                ) {
                    Text(
                        text = weather.cityName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = weather.country,
                        fontSize = 18.sp,
                    )
                    Text(
                        text = weather.temperature,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    AsyncImage(
                        model = weather.weatherIconUrl,
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        "Current Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    DetailItem(
                        "Humidity",
                        weather.humidity,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    DetailItem(
                        "Pressure",
                        weather.pressure,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    DetailItem(
                        "UV Index",
                        weather.uvIndex,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    DetailItem(
                        "Wind",
                        weather.wind,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun DetailItem(name: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(name, modifier = Modifier.weight(3f))
        Text(value, modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    val mockWeather = Weather(
        cityName = "Jakarta",
        country = "Indonesia",
        temperature = "30°C",
        weatherIconUrl = "",
        humidity = "50%",
        pressure = "1000 mBar",
        uvIndex = "High, 5",
        wind = "10 km/h, ENE"
    )
    MyWeatherTheme {
        WeatherScreen(weather = mockWeather)
    }
}
