package com.fasousa.vehiclesaleservice.presentation.response

import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus

import java.time.LocalDateTime

data class SaleResponse(
    val id: Long?,
    val vehicleId: Long,
    val cpf: String,
    val paymentCode: String,
    val paymentStatus: PaymentStatus,
    val saleDate: LocalDateTime
)
