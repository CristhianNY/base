package com.redfin.redfin.foodtrucks.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.domain.usecase.GetOpenFoodTrucksUseCase
import com.redfin.redfin.foodtrucks.presentation.state.FoodTrucksState
import com.redfin.redfin.support.ResultDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodTruckViewModel @Inject constructor(
    private val getOpenFoodTrucksUseCase: GetOpenFoodTrucksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FoodTrucksState>(FoodTrucksState.Idle)
    val state: StateFlow<FoodTrucksState> = _state.asStateFlow()

    private val _selectedFoodTruck = MutableStateFlow<FoodTruckModel?>(null)
    val selectedFoodTruck: StateFlow<FoodTruckModel?> = _selectedFoodTruck

    fun loadFoodTrucks(dayOfWeek: String, currentTime: String) {
        _state.value = FoodTrucksState.Loading
        viewModelScope.launch {
            when(val response = getOpenFoodTrucksUseCase(GetOpenFoodTrucksUseCase.Params(dayOfWeek, currentTime))) {
                is ResultDomain.Success -> _state.value = FoodTrucksState.Success(response.data)
                is ResultDomain.Error -> _state.value = FoodTrucksState.Error
            }
        }
    }

    fun selectFoodTruck(foodTruck: FoodTruckModel) {
        _selectedFoodTruck.value = foodTruck
    }

    fun clearSelection() {
        _selectedFoodTruck.value = null
    }
}
