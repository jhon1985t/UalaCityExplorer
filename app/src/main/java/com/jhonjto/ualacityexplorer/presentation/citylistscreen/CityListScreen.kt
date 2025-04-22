package com.jhonjto.ualacityexplorer.presentation.citylistscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun CityListScreen(
    navHostController: NavHostController,
    viewModel: CityViewModel = hiltViewModel()
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = viewModel::onQueryChange,
                label = { Text("Buscar ciudad") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = viewModel::toggleOnlyFavorites) {
                Text(if (viewModel.onlyFavorites) "Todos" else "Favoritos")
            }
        }

        LazyColumn {
            items(viewModel.filteredCities) { city ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("${city.name}, ${city.country}")
                        Text("(${city.lat}, ${city.lon})", style = MaterialTheme.typography.bodySmall)
                    }

                    Row {
                        IconButton(onClick = { viewModel.onToggleFavorite(city.id) }) {
                            Icon(
                                imageVector = if (viewModel.favoriteIds.contains(city.id))
                                    Icons.Default.Star else Icons.Default.Clear,
                                contentDescription = "Favorite"
                            )
                        }
                        IconButton(onClick = {
                            navHostController.navigate("city_detail/${city.id}")
                        }) {
                            Icon(Icons.Default.Info, contentDescription = "Detalles")
                        }

                        IconButton(onClick = {
                            navHostController.navigate("city_map/${city.lat}/${city.lon}")
                        }) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Ver en mapa")
                        }
                    }
                }
            }
        }
    }
}
