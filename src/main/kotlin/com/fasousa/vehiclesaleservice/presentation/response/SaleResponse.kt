package com.fasousa.vehiclesaleservice.presentation.response

import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus

data class SaleResponse(
    val id: Long?,
    val vehicleId: Long,
    val paymentCode: String,
    val paymentStatus: PaymentStatus
)
