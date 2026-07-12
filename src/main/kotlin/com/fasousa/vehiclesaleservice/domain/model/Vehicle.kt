package com.fasousa.vehiclesaleservice.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Vehicle(
    val id: Long? = null,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: BigDecimal,
    val status: VehicleStatus = VehicleStatus.AVAILABLE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
