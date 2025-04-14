package com.redfin.redfin.foodtrucks.di

import com.redfin.redfin.foodtrucks.data.repository.FoodTruckRepositoryImpl
import com.redfin.redfin.foodtrucks.domain.repository.FoodTruckRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodTruckRepositoryModule {
    @Singleton
    @Binds
    abstract fun provideFoodTruckRepository(impl: FoodTruckRepositoryImpl): FoodTruckRepository
}