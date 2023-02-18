package com.belizwp.myweather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MyWeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: MyWeatherScreen.Search.name
    )
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MyWeatherScreen.Search.name,
    ) {
        composable(route = MyWeatherScreen.Search.name) {
            SearchScreen(
                onFindButtonClicked = {
//                    viewModel.findWeather(it)
                    navController.navigate(MyWeatherScreen.Weather.name)
                }
            )
        }
        composable(route = MyWeatherScreen.Weather.name) {
            WeatherScreen(
                onCityNameClicked = {
                    navController.navigate(MyWeatherScreen.Map.name)
                },
                onBackPress = {
                    navController.popBackStack(MyWeatherScreen.Search.name, inclusive = false)
                }
            )
        }
        composable(route = MyWeatherScreen.Map.name) {
            MapScreen(
                onBackPress = {
                    navController.popBackStack(MyWeatherScreen.Weather.name, inclusive = false)
                }
            )
        }
    }
}
