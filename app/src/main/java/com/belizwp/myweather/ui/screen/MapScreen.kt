package com.belizwp.myweather.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.belizwp.myweather.ui.MyWeatherUiState
import com.belizwp.myweather.ui.theme.MyWeatherTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    uiState: MyWeatherUiState = MyWeatherUiState(),
    onNavigateBack: () -> Unit = {},
) {
    val weather = uiState.weather
    val location = LatLng(weather.lat, weather.lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }

    BackHandler {
        onNavigateBack()
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = location),
            title = weather.cityName,
            snippet = "Marker in ${weather.cityName}"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MyWeatherTheme {
        MapScreen()
    }
}
