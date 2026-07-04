package com.fasousa.vehiclesaleservice.presentation.request

data class PaymentWebhookRequest(
    val paymentCode: String,
    val status: String
)
