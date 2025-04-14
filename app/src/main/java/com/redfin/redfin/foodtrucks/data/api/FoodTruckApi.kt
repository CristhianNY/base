package com.redfin.redfin.foodtrucks.data.api

import com.redfin.redfin.foodtrucks.data.entity.FoodTruckEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodTruckApi {
    @GET("resource/bbb8-hzi6.json")
    suspend fun getFoodTrucks(
        @Query(value = "\$where", encoded = true) where: String,
        @Query(value = "\$order", encoded = true) order: String? = null
    ): Response<List<FoodTruckEntity>>
}