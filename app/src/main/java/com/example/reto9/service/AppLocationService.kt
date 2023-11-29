package com.example.reto9.service

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

class AppLocationService {

    companion object {
        private val TAG = "CurrentPlaceActivity"
        private const val PERMISSION_REQUEST_CODE = 9
    }

    fun checkPermissionThenFindCurrentPlace(context: Context): Boolean {
        when {
            (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED) -> {
                return true
            }

            shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            -> {
                Log.d(TAG, "Showing permission rationale dialog")
                // TODO: In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                return false
            }

            else -> {
                // Ask for both the ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions.
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PERMISSION_REQUEST_CODE
                )
                return false
            }
        }
    }
}