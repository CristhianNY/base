package com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel

sealed class FoodTrucksViewEvent {
    data class LoadFoodTrucks(val dayOfWeek: String, val currentTime: String) : FoodTrucksViewEvent()
    data class SelectFoodTruck(val foodTruck: FoodTruckModel) : FoodTrucksViewEvent()
    data object ClearSelection : FoodTrucksViewEvent()
}
