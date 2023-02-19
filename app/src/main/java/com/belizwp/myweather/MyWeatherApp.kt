package com.belizwp.myweather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belizwp.myweather.ui.MapScreen
import com.belizwp.myweather.ui.MyWeatherViewModel
import com.belizwp.myweather.ui.SearchScreen
import com.belizwp.myweather.ui.WeatherScreen
import com.belizwp.myweather.ui.components.DoubleBackPressToExit
import org.koin.androidx.compose.koinViewModel

enum class MyWeatherScreen {
    Search,
    Weather,
    Map,
}

@Composable
fun MyWeatherApp(
    modifier: Modifier = Modifier,
    viewModel: MyWeatherViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MyWeatherScreen.Search.name,
        modifier = modifier
    ) {
        composable(route = MyWeatherScreen.Search.name) {
            SearchScreen(
                onQueryChanged = { viewModel.updateQuery(it) },
                onSearchButtonClicked = { navController.navigate(MyWeatherScreen.Weather.name) }
            )
        }
        composable(route = MyWeatherScreen.Weather.name) {
            WeatherScreen(
                onCityNameClicked = { navController.navigate(MyWeatherScreen.Map.name) },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = MyWeatherScreen.Map.name) {
            MapScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }

    DoubleBackPressToExit()
}
