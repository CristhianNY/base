package com.redfin.redfin.foodtrucks.domain.repository

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.support.ResultDomain

interface FoodTruckRepository {
    suspend fun getOpenFoodTrucks(dayOfWeek: String, currentTime: String): ResultDomain<List<FoodTruckModel>>
}
