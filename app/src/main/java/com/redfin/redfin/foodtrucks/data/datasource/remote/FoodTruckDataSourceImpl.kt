package com.redfin.redfin.foodtrucks.data.datasource.remote

import com.redfin.redfin.foodtrucks.data.api.FoodTruckApi
import com.redfin.redfin.foodtrucks.data.entity.FoodTruckEntity
import com.redfin.redfin.support.BaseDataSource
import com.redfin.redfin.support.Result
import javax.inject.Inject

class FoodTruckDataSourceImpl @Inject constructor(private val api: FoodTruckApi) :
    BaseDataSource(), FoodTruckDataSource {

    override suspend fun getOpenFoodTrucks(dayOfWeek: String, currentTime: String): Result<List<FoodTruckEntity>> =
        getResult {
            val whereClause = """dayofweekstr="$dayOfWeek" AND start24 <= "$currentTime" AND end24 >= "$currentTime""""
            val orderClause = "applicant ASC"
            executeNetworkAction {
                api.getFoodTrucks(whereClause, orderClause)
            }
        }
}