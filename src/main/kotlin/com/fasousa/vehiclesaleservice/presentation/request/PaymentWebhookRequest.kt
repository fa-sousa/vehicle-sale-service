package com.fasousa.vehiclesaleservice.presentation.request

import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus

data class PaymentWebhookRequest(
    val paymentCode: String,
    val status: PaymentStatus
)
