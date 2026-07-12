package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus

interface ProcessPaymentWebhookUseCase {
    fun execute(
        paymentCode: String,
        status: PaymentStatus
    )
}
