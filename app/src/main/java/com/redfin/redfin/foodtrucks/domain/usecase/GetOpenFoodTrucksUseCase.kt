package com.redfin.redfin.foodtrucks.domain.usecase

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.support.UseCase

interface GetOpenFoodTrucksUseCase : UseCase<GetOpenFoodTrucksUseCase.Params, List<FoodTruckModel>> {
    data class Params(val dayOfWeek: String, val currentTime: String)
}
