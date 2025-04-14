package com.redfin.redfin.foodtrucks.presentation.list

import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.domain.usecase.GetOpenFoodTrucksUseCase
import com.redfin.redfin.foodtrucks.presentation.state.FoodTrucksState
import com.redfin.redfin.support.GenericError
import com.redfin.redfin.support.ResultDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class FoodTruckViewModelTest {

 private val testDispatcher = StandardTestDispatcher()

 @Mock
 private lateinit var getOpenFoodTrucksUseCase: GetOpenFoodTrucksUseCase

 private lateinit var viewModel: FoodTruckViewModel

 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)
  MockitoAnnotations.openMocks(this)
  viewModel = FoodTruckViewModel(getOpenFoodTrucksUseCase)
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }

 @Test
 fun `loadFoodTrucks sets state to Success when use case returns success`() = runTest {
  val fakeFoodTrucks = listOf(
   FoodTruckModel("Truck 1", "Address 1", "Description 1", "10:00", "18:00", null, null),
   FoodTruckModel("Truck 2", "Address 2", "Description 2", "11:00", "19:00", null, null)
  )

  whenever(getOpenFoodTrucksUseCase.invoke(any(), any()))
   .thenReturn(ResultDomain.Success(fakeFoodTrucks))

  viewModel.loadFoodTrucks("Friday", "12:00")
  advanceUntilIdle()

  val state = viewModel.state.value
  assertTrue("State should be Success", state is FoodTrucksState.Success)
  if (state is FoodTrucksState.Success)
   assertEquals(fakeFoodTrucks, state.foodTrucks)
 }

 @Test
 fun `loadFoodTrucks sets state to Error when use case returns error`() = runTest {
  whenever(getOpenFoodTrucksUseCase.invoke(any(), any()))
   .thenReturn(ResultDomain.Error(GenericError))

  viewModel.loadFoodTrucks("Friday", "12:00")
  advanceUntilIdle()

  val state = viewModel.state.value
  assertTrue("State should be Error", state is FoodTrucksState.Error)
 }

 @Test
 fun `selectFoodTruck sets selected food truck state`() = runTest {
  val foodTruck = FoodTruckModel("Truck 1", "Address", "Description", "10:00", "18:00", null, null)
  viewModel.selectFoodTruck(foodTruck)
  assertEquals(foodTruck, viewModel.selectedFoodTruck.value)
 }

 @Test
 fun `clearSelection resets selected food truck state to null`() = runTest {
  val foodTruck = FoodTruckModel("Truck 1", "Address", "Description", "10:00", "18:00", null, null)
  viewModel.selectFoodTruck(foodTruck)
  viewModel.clearSelection()
  assertNull(viewModel.selectedFoodTruck.value)
 }
}
