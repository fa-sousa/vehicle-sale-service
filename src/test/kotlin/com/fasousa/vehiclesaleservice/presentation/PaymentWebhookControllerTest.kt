package com.fasousa.vehiclesaleservice.presentation

import com.fasousa.vehiclesaleservice.application.usecase.ProcessPaymentWebhookUseCase
import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import com.fasousa.vehiclesaleservice.presentation.request.PaymentWebhookRequest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class PaymentWebhookControllerTest {

    private val processPaymentWebhookUseCase: ProcessPaymentWebhookUseCase = mock()

    private val controller = PaymentWebhookController(
        processPaymentWebhookUseCase = processPaymentWebhookUseCase
    )

    @Test
    fun `should process approved payment webhook`() {
        // GIVEN
        val request = PaymentWebhookRequest(
            paymentCode = "payment-123",
            status = PaymentStatus.APPROVED
        )

        // WHEN
        controller.processPaymentWebhook(request)

        // THEN
        verify(processPaymentWebhookUseCase).execute(
            paymentCode = "payment-123",
            status = PaymentStatus.APPROVED
        )
    }

    @Test
    fun `should process cancelled payment webhook`() {
        // GIVEN
        val request = PaymentWebhookRequest(
            paymentCode = "payment-456",
            status = PaymentStatus.CANCELLED
        )

        // WHEN
        controller.processPaymentWebhook(request)

        // THEN
        verify(processPaymentWebhookUseCase).execute(
            paymentCode = "payment-456",
            status = PaymentStatus.CANCELLED
        )
    }
}
