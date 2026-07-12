package com.fasousa.vehiclesaleservice.presentation.response

import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import java.math.BigDecimal

data class VehicleResponse(
    val id: Long?,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: BigDecimal,
    val status: VehicleStatus
)
