package com.redfin.redfin.foodtrucks.data.repository

import com.redfin.redfin.foodtrucks.data.datasource.remote.FoodTruckDataSource
import com.redfin.redfin.foodtrucks.data.entity.FoodTruckEntity
import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.domain.repository.FoodTruckRepository
import com.redfin.redfin.support.GenericError
import com.redfin.redfin.support.Result
import com.redfin.redfin.support.ResultDomain
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class FoodTruckRepositoryImplTest {

    @Mock
    private lateinit var dataSource: FoodTruckDataSource

    private lateinit var repository: FoodTruckRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = FoodTruckRepositoryImpl(dataSource)
    }

    @Test
    fun `getOpenFoodTrucks returns Success when dataSource returns successful result`() = runTest {
        val entity1 = FoodTruckEntity("Truck 1", "Address 1", "Description 1", "10:00", "18:00", null, null)
        val entity2 = FoodTruckEntity("Truck 2", "Address 2", "Description 2", "11:00", "19:00", null, null)

        // Create a dummy successful result using your Result.success() helper.
        val dummySuccess = Result.success(listOf(entity1, entity2))
        whenever(dataSource.getOpenFoodTrucks("Friday", "12:00")).thenReturn(dummySuccess)

        val result = repository.getOpenFoodTrucks("Friday", "12:00")
        val expected = listOf(
            FoodTruckModel("Truck 1", "Address 1", "Description 1", "10:00", "18:00", null, null),
            FoodTruckModel("Truck 2", "Address 2", "Description 2", "11:00", "19:00", null, null)
        )

        assertTrue(result is ResultDomain.Success)
        if (result is ResultDomain.Success) {
            assertEquals(expected, result.data)
        }
    }

    @Test
    fun `getOpenFoodTrucks returns Error when dataSource returns error result`() = runTest {
        // Create a dummy error result using your Result.error() helper.
        val dummyError = Result.error<List<FoodTruckEntity>>(Exception("Bad Request"), 400, "Bad Request")
        whenever(dataSource.getOpenFoodTrucks("Friday", "12:00")).thenReturn(dummyError)

        val result = repository.getOpenFoodTrucks("Friday", "12:00")
        assertTrue(result is ResultDomain.Error)
        if (result is ResultDomain.Error) {
            assertEquals(GenericError, result.error)
        }
    }
}
