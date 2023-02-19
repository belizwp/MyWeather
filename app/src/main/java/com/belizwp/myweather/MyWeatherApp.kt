package com.belizwp.myweather

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belizwp.myweather.ui.MapScreen
import com.belizwp.myweather.ui.MyWeatherViewModel
import com.belizwp.myweather.ui.SearchScreen
import com.belizwp.myweather.ui.WeatherScreen
import com.belizwp.myweather.ui.components.DoubleBackPressToExit
import com.belizwp.myweather.ui.components.LoadingDialog
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
    if (viewModel.isLoading.value) {
        LoadingDialog()
    }

    NavHost(
        navController = navController,
        startDestination = MyWeatherScreen.Search.name,
        modifier = modifier
    ) {
        composable(route = MyWeatherScreen.Search.name) {
            val context = LocalContext.current
            SearchScreen(
                query = viewModel.query.value,
                onQueryChanged = { viewModel.updateQuery(it) },
                onSearchButtonClicked = {
                    viewModel.search(
                        onSearchSuccess = { navController.navigate(MyWeatherScreen.Weather.name) },
                        onSearchError = { errMsg ->
                            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
            )
        }
        composable(route = MyWeatherScreen.Weather.name) {
            val context = LocalContext.current
            WeatherScreen(
                weather = viewModel.weather.value,
                onCityNameClicked = { navController.navigate(MyWeatherScreen.Map.name) },
                navigateBack = { navController.popBackStack() },
                refreshing = viewModel.isRefreshing.value,
                onRefresh = { viewModel.refresh(
                    onRefreshSuccess = {
                        Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()
                    },
                    onRefreshError = { errMsg ->
                        Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
                    }
                ) },
            )
        }
        composable(route = MyWeatherScreen.Map.name) {
            MapScreen(
                weather = viewModel.weather.value,
                navigateBack = { navController.popBackStack() }
            )
        }
    }

    DoubleBackPressToExit()
}
