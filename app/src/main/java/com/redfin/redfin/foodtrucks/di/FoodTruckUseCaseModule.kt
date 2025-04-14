package com.redfin.redfin.foodtrucks.di

import com.redfin.redfin.foodtrucks.domain.usecase.GetOpenFoodTrucksUseCase
import com.redfin.redfin.foodtrucks.domain.usecase.GetOpenFoodTrucksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodTruckUseCaseModule {
    @Singleton
    @Binds
    abstract fun provideGetOpenFoodTrucksUseCase(impl: GetOpenFoodTrucksUseCaseImpl): GetOpenFoodTrucksUseCase
}