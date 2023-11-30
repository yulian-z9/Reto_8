package com.example.reto9.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reto9.models.UiMapScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiMapScreen())
    val uiState: StateFlow<UiMapScreen> = _uiState.asStateFlow()
    private val requestCode = 9

    init {
        _uiState.update { it.copy(showMap = false) }
    }

    fun initMap(context: Context) {

        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION

        if (ContextCompat.checkSelfPermission(
                context,
                locationPermission
            ) == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                context,
                coarsePermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            _uiState.update { it.copy(showMap = true) }
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCode
            )
            viewModelScope.launch { waitForPermissionUpdate(context) }
        }
    }

    private suspend fun waitForPermissionUpdate(context: Context) {
        delay(1000)
        initMap(context)
    }

    fun startMapNavigation(fusedLocationClient: FusedLocationProviderClient, context: Context) {
        Log.d("test", "Camera navigation")
        _uiState.update { it.copy(updateMap = false) }
        viewModelScope.launch {
            navigateToUserLocation(fusedLocationClient, context)
            delay(500)
            val position = CameraPosition.fromLatLngZoom(_uiState.value.userLocation, 14f)
            val state = CameraPositionState(position = position)
            _uiState.update { it.copy(cameraState = state) }
        }


    }

    private fun navigateToUserLocation(
        fusedLocationClient: FusedLocationProviderClient,
        context: Context
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        Log.d("test", "${location.latitude} ${location.longitude}")
                        _uiState.update {
                            it.copy(
                                userLocation = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        }
                    }
                }
        }

    }
}