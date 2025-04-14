package com.redfin.redfin.foodtrucks.presentation.state

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel

sealed class FoodTrucksState {
    data object Idle : FoodTrucksState()
    data object Loading : FoodTrucksState()
    data class Success(val foodTrucks: List<FoodTruckModel>?) : FoodTrucksState()
    data object Error : FoodTrucksState()
}
