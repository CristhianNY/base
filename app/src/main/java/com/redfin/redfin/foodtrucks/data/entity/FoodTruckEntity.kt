package com.redfin.redfin.foodtrucks.data.entity

import com.google.gson.annotations.SerializedName
import com.redfin.redfin.foodtrucks.domain.model.FoodTruckModel

data class FoodTruckEntity(
    @SerializedName("applicant")
    val name: String?,
    @SerializedName("location")
    val address: String?,
    @SerializedName("locationdesc")
    val description: String?,
    @SerializedName("start24")
    val startTime: String?,
    @SerializedName("end24")
    val endTime: String?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?
)

fun FoodTruckEntity.toModel() = FoodTruckModel(
    name,
    address,
    description,
    startTime,
    endTime,
    latitude,
    longitude
)