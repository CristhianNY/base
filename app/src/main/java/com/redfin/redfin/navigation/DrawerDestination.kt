package com.redfin.redfin.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerDestination(val route: String, val title: String, val icon: ImageVector) {
    object List : DrawerDestination("list", "Food Trucks", Icons.Default.List)
    object Map : DrawerDestination("map", "Map", Icons.Default.Map)
    object About : DrawerDestination("about", "About", Icons.Default.Info)

    companion object {
        val items = listOf(List, Map, About)
    }
}
