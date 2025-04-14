package com.redfin.redfin.foodtrucks.data.repository

import com.redfin.redfin.foodtrucks.data.datasource.remote.FoodTruckDataSource
import com.redfin.redfin.foodtrucks.data.entity.toModel
import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.domain.repository.FoodTruckRepository
import com.redfin.redfin.support.GenericErrorMapper
import com.redfin.redfin.support.ResultDomain
import com.redfin.redfin.support.baseResponseErrorHandler
import javax.inject.Inject

class FoodTruckRepositoryImpl @Inject constructor(
    private val dataSource: FoodTruckDataSource
) : FoodTruckRepository {
    override suspend fun getOpenFoodTrucks(dayOfWeek: String, currentTime: String): ResultDomain<List<FoodTruckModel>> =
        baseResponseErrorHandler(
            GenericErrorMapper,
            dataSource.getOpenFoodTrucks(dayOfWeek, currentTime)
        ) { result ->
            ResultDomain.Success(result.map { it.toModel() })
        }
}