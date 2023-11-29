package com.example.reto9.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reto9.R
import com.example.reto9.ui.MainAppBar
import com.example.reto9.viewmodel.AppViewModelProvider
import com.example.reto9.viewmodel.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val uiState by mapViewModel.uiState.collectAsState()
    val context = LocalContext.current

    when {
        !uiState.showMap->{ mapViewModel.initMap(context) }
    }

    //content
    Scaffold(
        topBar = {
            MainAppBar(
                label = stringResource(id = R.string.map_label),
                trailingIcon = Icons.Default.Search,
                leadingAction = {mapViewModel.initMap(context)},
                trailingAction = {}
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if(uiState.showMap)
                MapBody()
            else
                Surface {}
        }
    }
}

@Composable
fun MapBody() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )

    }
}