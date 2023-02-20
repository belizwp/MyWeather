package com.belizwp.myweather.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belizwp.myweather.ui.components.DoubleBackPressToExit
import com.belizwp.myweather.ui.components.LoadingDialog
import com.belizwp.myweather.ui.screen.MapScreen
import com.belizwp.myweather.ui.screen.SearchScreen
import com.belizwp.myweather.ui.screen.WeatherScreen
import kotlinx.coroutines.flow.collectLatest
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
    val context = LocalContext.current

    DoubleBackPressToExit()

    LaunchedEffect(Unit) {
        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is Event.SearchSucceeded -> {
                    navController.navigate(MyWeatherScreen.Weather.name)
                }
                is Event.SearchFailed -> {
                    Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
                }
                is Event.RefreshSucceeded -> {
                    Toast.makeText(context, "Refresh succeeded", Toast.LENGTH_SHORT).show()
                }
                is Event.RefreshFailed -> {
                    Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    AnimatedVisibility(visible = viewModel.isLoading.value) {
        LoadingDialog()
    }

    NavHost(
        navController = navController,
        startDestination = MyWeatherScreen.Search.name,
        modifier = modifier
    ) {
        composable(route = MyWeatherScreen.Search.name) {
            SearchScreen(
                query = viewModel.query.value,
                isQueryValid = viewModel.isQueryValid.value,
                onQueryChanged = { viewModel.updateQuery(it) },
                onSearchButtonClicked = { viewModel.search() },
            )
        }
        composable(route = MyWeatherScreen.Weather.name) {
            WeatherScreen(
                weather = viewModel.weather.value,
                onCityNameClicked = { navController.navigate(MyWeatherScreen.Map.name) },
                navigateBack = { navController.popBackStack() },
                refreshing = viewModel.isRefreshing.value,
                onRefresh = { viewModel.refresh() },
            )
        }
        composable(route = MyWeatherScreen.Map.name) {
            MapScreen(
                weather = viewModel.weather.value,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
