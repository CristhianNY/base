package com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract

sealed class FoodTrucksViewEffect {
    data class ShowError(val message: String) : FoodTrucksViewEffect()
}
