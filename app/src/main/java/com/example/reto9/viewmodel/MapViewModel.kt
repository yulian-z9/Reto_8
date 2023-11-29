package com.example.reto9.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reto9.models.UiMapScreen
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
            Log.d("test", "Permisos grantizados? ${_uiState.value.showMap}")
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCode
            )
            Log.d("test", "Permisos grantizados? ${_uiState.value.showMap}")
            viewModelScope.launch { waitForPermissionUpdate(context) }
        }
    }

    private suspend fun waitForPermissionUpdate(context: Context){
        delay(1000)
        initMap(context)
    }
}