package com.example.reto9.viewmodel

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer
        initializer {
            MapViewModel()
        }
    }
}