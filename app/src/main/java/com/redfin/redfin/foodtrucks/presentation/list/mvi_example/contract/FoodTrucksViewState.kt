package com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel

sealed class FoodTrucksViewState {
    data object Loading : FoodTrucksViewState()
    data class Success(val foodTrucks: List<FoodTruckModel>?) : FoodTrucksViewState()
    data object Error : FoodTrucksViewState()
}
