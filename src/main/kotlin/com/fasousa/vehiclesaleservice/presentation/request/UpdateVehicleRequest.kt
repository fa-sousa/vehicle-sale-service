package com.fasousa.vehiclesaleservice.presentation.request

import java.math.BigDecimal

data class UpdateVehicleRequest(
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: BigDecimal
)
