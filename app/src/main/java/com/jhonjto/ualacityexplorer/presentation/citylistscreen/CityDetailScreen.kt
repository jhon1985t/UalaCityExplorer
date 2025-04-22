package com.jhonjto.ualacityexplorer.presentation.citylistscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    cityId: Int,
    navController: NavHostController,
    viewModel: CityViewModel = hiltViewModel()
) {
    val city = viewModel.getCityById(cityId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de ciudad") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        city?.let {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)) {

                Text("Ciudad: ${city.name}", style = MaterialTheme.typography.headlineMedium)
                Text("País: ${city.country}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Latitud: ${city.lat}")
                Text("Longitud: ${city.lon}")
                Text("ID: ${city.id}")

                Button(
                    onClick = { navController.navigate("city_map/${city.lat}/${city.lon}") },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ver en Mapa")
                }

            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ciudad no encontrada")
            }
        }
    }
}
