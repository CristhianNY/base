package com.redfin.redfin.foodtrucks.presentation.list.mvi_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel
import com.redfin.redfin.foodtrucks.domain.usecase.GetOpenFoodTrucksUseCase
import com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract.FoodTrucksViewEffect
import com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract.FoodTrucksViewEvent
import com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract.FoodTrucksViewState
import com.redfin.redfin.support.ResultDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodTrucksMVIViewModel @Inject constructor(
    private val getOpenFoodTrucksUseCase: GetOpenFoodTrucksUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<FoodTrucksViewState>(FoodTrucksViewState.Loading)
    val viewState: StateFlow<FoodTrucksViewState> = _viewState.asStateFlow()

    private val _viewEffect = MutableSharedFlow<FoodTrucksViewEffect>()
    val viewEffect: SharedFlow<FoodTrucksViewEffect> = _viewEffect

    private val _selectedFoodTruck = MutableStateFlow<FoodTruckModel?>(null)
    val selectedFoodTruck: StateFlow<FoodTruckModel?> = _selectedFoodTruck.asStateFlow()

    init {
        onEvent(FoodTrucksViewEvent.LoadFoodTrucks("Friday", "12:00"))
    }

    fun onEvent(event: FoodTrucksViewEvent) {
        when (event) {
            is FoodTrucksViewEvent.LoadFoodTrucks -> {
                loadFoodTrucks(event.dayOfWeek, event.currentTime)
            }

            is FoodTrucksViewEvent.SelectFoodTruck -> {
                _selectedFoodTruck.value = event.foodTruck
            }

            FoodTrucksViewEvent.ClearSelection -> {
                _selectedFoodTruck.value = null
            }
        }
    }

    private fun loadFoodTrucks(dayOfWeek: String, currentTime: String) {
        _viewState.value = FoodTrucksViewState.Loading
        viewModelScope.launch {
            when (val result = getOpenFoodTrucksUseCase(
                GetOpenFoodTrucksUseCase.Params(dayOfWeek, currentTime)
            )) {
                is ResultDomain.Success -> {
                    _viewState.value = FoodTrucksViewState.Success(result.data)
                }

                is ResultDomain.Error -> {
                    _viewState.value = FoodTrucksViewState.Error
                    _viewEffect.emit(FoodTrucksViewEffect.ShowError("Failed to load food trucks"))
                }
            }
        }
    }
}
