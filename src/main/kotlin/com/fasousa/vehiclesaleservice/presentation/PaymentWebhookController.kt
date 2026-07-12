package com.fasousa.vehiclesaleservice.presentation

import com.fasousa.vehiclesaleservice.application.usecase.ProcessPaymentWebhookUseCase
import com.fasousa.vehiclesaleservice.presentation.request.PaymentWebhookRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payments")
class PaymentWebhookController(
    private val processPaymentWebhookUseCase: ProcessPaymentWebhookUseCase
) {

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun processPaymentWebhook(
        @RequestBody request: PaymentWebhookRequest
    ) {
        processPaymentWebhookUseCase.execute(
            paymentCode = request.paymentCode,
            status = request.status
        )
    }
}
