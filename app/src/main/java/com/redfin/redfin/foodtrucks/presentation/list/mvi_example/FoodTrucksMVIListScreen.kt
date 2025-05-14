package com.redfin.redfin.foodtrucks.presentation.list.mvi_example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.redfin.redfin.R
import com.redfin.redfin.foodtrucks.presentation.details.FoodTruckDetailBottomSheet
import com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract.FoodTrucksViewEffect
import com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract.FoodTrucksViewEvent
import com.redfin.redfin.foodtrucks.presentation.list.mvi_example.contract.FoodTrucksViewState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FoodTrucksMVIListScreen(
    viewModel: FoodTrucksMVIViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val selectedFoodTruck by viewModel.selectedFoodTruck.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.onEvent(FoodTrucksViewEvent.LoadFoodTrucks("Friday", "12:00"))
            isRefreshing = false
        }
    )

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collectLatest { effect ->
            when (effect) {
                is FoodTrucksViewEffect.ShowError -> {
                    println("Error: ${effect.message}")
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        when (viewState) {
            is FoodTrucksViewState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is FoodTrucksViewState.Success -> {
                val foodTrucks = (viewState as FoodTrucksViewState.Success).foodTrucks ?: emptyList()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(foodTrucks) { foodTruck ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(FoodTrucksViewEvent.SelectFoodTruck(foodTruck))
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(foodTruck.name.orEmpty(), style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(foodTruck.address.orEmpty(), style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(foodTruck.description.orEmpty(), style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    stringResource(
                                        R.string.open_hours,
                                        foodTruck.startTime.orEmpty(),
                                        foodTruck.endTime.orEmpty()
                                    ),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }

            is FoodTrucksViewState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_loading_food_trucks),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    selectedFoodTruck?.let { foodTruck ->
        FoodTruckDetailBottomSheet(
            foodTruck = foodTruck,
            onDismiss = {
                viewModel.onEvent(FoodTrucksViewEvent.ClearSelection)
            }
        )
    }
}
