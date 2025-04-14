package com.redfin.redfin.foodtrucks.data.datasource.remote

import com.redfin.redfin.foodtrucks.data.entity.FoodTruckEntity
import com.redfin.redfin.support.Result
interface FoodTruckDataSource {
    suspend fun getOpenFoodTrucks(dayOfWeek: String, currentTime: String): Result<List<FoodTruckEntity>>
}