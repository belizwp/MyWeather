package com.belizwp.myweather

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.belizwp.myweather.ui.MapScreen
import com.belizwp.myweather.ui.SearchScreen
import com.belizwp.myweather.ui.WeatherScreen
import com.belizwp.myweather.ui.WeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class MyWeatherScreen {
    Search,
    Weather,
    Map,
}

@Composable
fun MyWeatherApp(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MyWeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: MyWeatherScreen.Search.name
    )
    val searchUiState by viewModel.searchUiState.collectAsState()
    val weatherUiState by viewModel.weatherUiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MyWeatherScreen.Search.name,
    ) {
        composable(route = MyWeatherScreen.Search.name) {
            SearchScreen(
                uiState = searchUiState,
                onQueryChanged = { viewModel.updateQuery(it) },
                onSearchButtonClicked = {
                    if (viewModel.findWeather()) {
                        navController.navigate(MyWeatherScreen.Weather.name)
                    }
                }
            )
        }
        composable(route = MyWeatherScreen.Weather.name) {
            WeatherScreen(
                onCityNameClicked = {
                    navController.navigate(MyWeatherScreen.Map.name)
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = MyWeatherScreen.Map.name) {
            MapScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }

    DoubleBackPressToExit()
}

@Composable
fun DoubleBackPressToExit() {
    var pressBackOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        val activity = (context as? Activity)
        if (pressBackOnce) {
            activity?.finish()
        } else {
            Toast.makeText(context, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
            pressBackOnce = true
            coroutineScope.launch {
                delay(2000)
                pressBackOnce = false
            }
        }
    }
}
