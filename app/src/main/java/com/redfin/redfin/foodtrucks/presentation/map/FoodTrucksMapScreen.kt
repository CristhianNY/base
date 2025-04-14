package com.redfin.redfin.foodtrucks.presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.presentation.details.FoodTruckDetailBottomSheet
import com.redfin.redfin.foodtrucks.presentation.list.FoodTruckViewModel
import com.redfin.redfin.foodtrucks.presentation.state.FoodTrucksState

private const val DEFAULT_LATITUDE = 37.7749
private const val DEFAULT_LONGITUDE = -122.4194
private const val DEFAULT_ZOOM = 12f

@Composable
fun FoodTrucksMapScreen(
    foodTruckViewModel: FoodTruckViewModel = hiltViewModel()
) {
    val state by foodTruckViewModel.state.collectAsStateWithLifecycle()
    val selectedFoodTruck by foodTruckViewModel.selectedFoodTruck.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { foodTruckViewModel.loadFoodTrucks("Friday", "12:00") }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE), DEFAULT_ZOOM)
    }
    if (state is FoodTrucksState.Success) {
        val foodTrucks = (state as FoodTrucksState.Success).foodTrucks ?: emptyList()
        if (foodTrucks.isNotEmpty()) {
            val firstFoodTruck = foodTrucks.first()
            val lat = firstFoodTruck.latitude?.toDoubleOrNull() ?: DEFAULT_LATITUDE
            val lng = firstFoodTruck.longitude?.toDoubleOrNull() ?: DEFAULT_LONGITUDE
            LaunchedEffect(foodTrucks) {
                cameraPositionState.animate(
                    update = com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                        LatLng(lat, lng),
                        DEFAULT_ZOOM
                    )
                )
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is FoodTrucksState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is FoodTrucksState.Success -> {
                val foodTrucks = (state as FoodTrucksState.Success).foodTrucks ?: emptyList()
                GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
                    foodTrucks.forEach { foodTruck ->
                        val lat = foodTruck.latitude?.toDoubleOrNull() ?: DEFAULT_LATITUDE
                        val lng = foodTruck.longitude?.toDoubleOrNull() ?: DEFAULT_LONGITUDE
                        Marker(
                            state = MarkerState(LatLng(lat, lng)),
                            title = foodTruck.name,
                            snippet = "${foodTruck.startTime} - ${foodTruck.endTime}",
                            onClick = {
                                foodTruckViewModel.selectFoodTruck(foodTruck)
                                true
                            }
                        )
                    }
                }
            }
            is FoodTrucksState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error loading food trucks", color = MaterialTheme.colorScheme.error, fontSize = 16.sp)
                }
            }
            FoodTrucksState.Idle -> {}
        }
    }
    selectedFoodTruck?.let { foodTruck ->
        FoodTruckDetailBottomSheet(
            foodTruck = foodTruck,
            onDismiss = { foodTruckViewModel.clearSelection() }
        )
    }
}
