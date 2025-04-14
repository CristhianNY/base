package com.redfin.redfin.foodtrucks.data.datasource.remote

import com.redfin.redfin.foodtrucks.data.api.FoodTruckApi
import com.redfin.redfin.foodtrucks.data.entity.FoodTruckEntity
import com.redfin.redfin.support.Result
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response

class FoodTruckDataSourceImplTest {

 @Mock
 private lateinit var api: FoodTruckApi

 private lateinit var dataSource: FoodTruckDataSourceImpl

 @Before
 fun setup() {
  MockitoAnnotations.openMocks(this)
  dataSource = FoodTruckDataSourceImpl(api)
 }

 @Test
 fun `getOpenFoodTrucks returns success when API call is successful`() = runTest {
  val entity1 = FoodTruckEntity("Truck 1", "Address 1", "Desc 1", "10:00", "18:00", null, null)
  val entity2 = FoodTruckEntity("Truck 2", "Address 2", "Desc 2", "11:00", "19:00", null, null)
  val entities = listOf(entity1, entity2)
  val response = Response.success(entities)
  whenever(api.getFoodTrucks(any(), any())).thenReturn(response)

  val result = dataSource.getOpenFoodTrucks("Friday", "12:00")
  assertTrue(result.status is Result.Status.SUCCESS)
  assertEquals(entities, result.data)
 }

 @Test
 fun `getOpenFoodTrucks returns error when API call returns error`() = runTest {
  val errorBody = "Not Found".toResponseBody("application/json".toMediaTypeOrNull())
  val errorResponse = Response.error<List<FoodTruckEntity>>(404, errorBody)
  whenever(api.getFoodTrucks(any(), any())).thenReturn(errorResponse)

  val result = dataSource.getOpenFoodTrucks("Friday", "12:00")
  assertTrue(result.status is Result.Status.ERROR)
 }
}
