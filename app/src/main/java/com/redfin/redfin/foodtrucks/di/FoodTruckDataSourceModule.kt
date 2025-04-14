package com.redfin.redfin.foodtrucks.di

import com.redfin.redfin.foodtrucks.data.datasource.remote.FoodTruckDataSource
import com.redfin.redfin.foodtrucks.data.datasource.remote.FoodTruckDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodTruckDataSourceModule {
    @Singleton
    @Binds
    abstract fun provideFoodTruckDataSource(impl: FoodTruckDataSourceImpl): FoodTruckDataSource
}