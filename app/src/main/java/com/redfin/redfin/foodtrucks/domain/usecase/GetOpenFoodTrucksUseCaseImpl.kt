package com.redfin.redfin.foodtrucks.domain.usecase

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.domain.repository.FoodTruckRepository
import com.redfin.redfin.support.ResultDomain
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetOpenFoodTrucksUseCaseImpl @Inject constructor(
    private val repository: FoodTruckRepository
) : GetOpenFoodTrucksUseCase {
    override suspend fun invoke(
        params: GetOpenFoodTrucksUseCase.Params,
        context: CoroutineContext
    ): ResultDomain<List<FoodTruckModel>> {
        return repository.getOpenFoodTrucks(params.dayOfWeek, params.currentTime)
    }
}