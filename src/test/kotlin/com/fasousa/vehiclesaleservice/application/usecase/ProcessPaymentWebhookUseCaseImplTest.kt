package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.ProcessPaymentWebhookUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.*
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
class ProcessPaymentWebhookUseCaseImplTest {

    @Mock
    lateinit var saleRepository: SaleRepository

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var useCase: ProcessPaymentWebhookUseCaseImpl

    @Test
    fun `GIVEN approved payment WHEN execute THEN mark vehicle as sold`() {

        val sale = Sale(
            id = 1L,
            vehicleId = 1L,
            cpf = "123",
            paymentCode = "PAY123"
        )

        val vehicle = Vehicle(
            id = 1L,
            brand = "Toyota",
            model = "Corolla",
            year = 2024,
            color = "White",
            price = BigDecimal("100000")
        )

        `when`(saleRepository.findByPaymentCode("PAY123"))
            .thenReturn(sale)

        `when`(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        useCase.execute("PAY123", PaymentStatus.APPROVED)

        verify(vehicleRepository).save(any())
    }

    @Test
    fun `GIVEN payment not found WHEN execute THEN throw exception`() {

        `when`(saleRepository.findByPaymentCode("INVALID"))
            .thenReturn(null)

        assertThrows<IllegalArgumentException> {
            useCase.execute("INVALID", PaymentStatus.APPROVED)
        }
    }
}
