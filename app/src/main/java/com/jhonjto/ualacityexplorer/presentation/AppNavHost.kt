package com.jhonjto.ualacityexplorer.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhonjto.ualacityexplorer.presentation.citylistscreen.CityDetailScreen
import com.jhonjto.ualacityexplorer.presentation.citylistscreen.CityListScreen
import com.jhonjto.ualacityexplorer.presentation.citylistscreen.CityMapScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "city_list") {
        composable("city_list") { CityListScreen(navController) }
        composable("city_detail/{cityId}") { backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")?.toIntOrNull()
            if (cityId == null) navController.popBackStack() else CityDetailScreen(cityId, navController)
        }
        composable("city_map/{lat}/{lon}") { backStackEntry ->
            val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull()
            val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull()
            if (lat != null && lon != null) {
                CityMapScreen(lat, lon)
            }
        }
    }
}
