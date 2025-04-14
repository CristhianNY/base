package com.redfin.redfin.foodtrucks.di

import com.redfin.redfin.foodtrucks.data.api.FoodTruckApi
import com.redfin.redfin.support.di.BASE_PATH_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FoodTruckApiModule {
    @Singleton
    @Provides
    fun provideFoodTruckApi(@Named(BASE_PATH_URL) retrofit: Retrofit): FoodTruckApi {
        return retrofit.create(FoodTruckApi::class.java)
    }
}
