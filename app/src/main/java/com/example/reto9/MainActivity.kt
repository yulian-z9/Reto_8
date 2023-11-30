package com.example.reto9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.reto9.screens.MapScreen
import com.example.reto9.ui.theme.Reto8Theme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

private lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var placesClient: PlacesClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Initialize the SDK with the Google Maps Platform API key
        Places.initialize(this, "AIzaSyBQojR1JcKQA7vFsq2R8MFUeTK3WiNppVQ")
        placesClient = Places.createClient(this)
        setContent {
            Reto8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapScreen(fusedLocationClient)
                }
            }
        }
    }
}

