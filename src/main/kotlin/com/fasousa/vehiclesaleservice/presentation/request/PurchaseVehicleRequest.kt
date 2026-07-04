package com.fasousa.vehiclesaleservice.presentation.request

data class PurchaseVehicleRequest(
    val vehicleId: Long,
    val cpf: String
)
