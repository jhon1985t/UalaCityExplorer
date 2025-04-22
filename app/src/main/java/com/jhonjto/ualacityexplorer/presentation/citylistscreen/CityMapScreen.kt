package com.jhonjto.ualacityexplorer.presentation.citylistscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityMapScreen(
    lat: Double,
    lon: Double
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 10f)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mapa") })
        }
    ) { padding ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(lat, lon)),
                title = "Ciudad seleccionada"
            )
        }
    }
}
