package com.fasousa.vehiclesaleservice.domain.model

import java.time.LocalDateTime

data class Sale(
    val id: Long? = null,
    val vehicleId: Long,
    val cpf: String,
    val saleDate: LocalDateTime = LocalDateTime.now(),
    val paymentCode: String,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
