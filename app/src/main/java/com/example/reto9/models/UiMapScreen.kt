package com.example.reto9.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

data class UiMapScreen(
    var showMap: Boolean = false,
    var updateMap: Boolean = true,
    var userLocation: LatLng = LatLng(1.35, 103.87),
    var cameraState: CameraPositionState = CameraPositionState()
)