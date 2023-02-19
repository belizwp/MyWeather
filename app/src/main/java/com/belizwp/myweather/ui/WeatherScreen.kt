package com.belizwp.myweather.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.belizwp.myweather.ui.theme.MyWeatherTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel(),
    onCityNameClicked: () -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    BackHandler() {
        onBackPressed()
    }

    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })

    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.secondary)
                        .clickable { onCityNameClicked() }
                        .padding(32.dp)
                ) {
                    Text(
                        text = "Tokyo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "Japan",
                        fontSize = 18.sp,
                    )
                    Text(
                        "32°C",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0017_cloudy_with_light_rain.png")
                            .crossfade(true)
                            .build(),
                        error = null,
                        placeholder = null,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
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
                        "50%",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    DetailItem(
                        "Pressure",
                        "1000 mBar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    DetailItem(
                        "UV Index",
                        "High, 5",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    DetailItem(
                        "Wind",
                        "1km/h ENE",
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
fun HomeScreenPreview() {
    MyWeatherTheme {
        WeatherScreen()
    }
}
