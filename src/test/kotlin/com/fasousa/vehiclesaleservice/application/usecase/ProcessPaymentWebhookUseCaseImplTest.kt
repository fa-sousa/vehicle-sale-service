package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.ProcessPaymentWebhookUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import com.fasousa.vehiclesaleservice.domain.model.Sale
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ProcessPaymentWebhookUseCaseImplTest {

    @Mock
    lateinit var saleRepository: SaleRepository

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var useCase: ProcessPaymentWebhookUseCaseImpl

    @Test
    fun `GIVEN approved payment WHEN execute THEN update sale and mark vehicle as sold`() {
        // GIVEN
        val sale = createSale(paymentCode = "PAY123")
        val vehicle = createVehicle(VehicleStatus.PENDING_PAYMENT)

        whenever(saleRepository.findByPaymentCode("PAY123"))
            .thenReturn(sale)

        whenever(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        val saleCaptor = argumentCaptor<Sale>()
        val vehicleCaptor = argumentCaptor<Vehicle>()

        // WHEN
        useCase.execute("PAY123", PaymentStatus.APPROVED)

        // THEN
        verify(saleRepository).save(saleCaptor.capture())
        verify(vehicleRepository).save(vehicleCaptor.capture())

        assertEquals(PaymentStatus.APPROVED, saleCaptor.firstValue.paymentStatus)
        assertEquals(VehicleStatus.SOLD, vehicleCaptor.firstValue.status)
    }

    @Test
    fun `GIVEN cancelled payment WHEN execute THEN update sale and return vehicle to available`() {
        // GIVEN
        val sale = createSale(paymentCode = "PAY456")
        val vehicle = createVehicle(VehicleStatus.PENDING_PAYMENT)

        whenever(saleRepository.findByPaymentCode("PAY456"))
            .thenReturn(sale)

        whenever(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        val saleCaptor = argumentCaptor<Sale>()
        val vehicleCaptor = argumentCaptor<Vehicle>()

        // WHEN
        useCase.execute("PAY456", PaymentStatus.CANCELLED)

        // THEN
        verify(saleRepository).save(saleCaptor.capture())
        verify(vehicleRepository).save(vehicleCaptor.capture())

        assertEquals(PaymentStatus.CANCELLED, saleCaptor.firstValue.paymentStatus)
        assertEquals(VehicleStatus.AVAILABLE, vehicleCaptor.firstValue.status)
    }

    @Test
    fun `GIVEN pending payment WHEN execute THEN update sale and do not change vehicle`() {
        // GIVEN
        val sale = createSale(paymentCode = "PAY789")
        val vehicle = createVehicle(VehicleStatus.PENDING_PAYMENT)

        whenever(saleRepository.findByPaymentCode("PAY789"))
            .thenReturn(sale)

        whenever(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        val saleCaptor = argumentCaptor<Sale>()

        // WHEN
        useCase.execute("PAY789", PaymentStatus.PENDING)

        // THEN
        verify(saleRepository).save(saleCaptor.capture())
        verify(vehicleRepository, never()).save(any())

        assertEquals(PaymentStatus.PENDING, saleCaptor.firstValue.paymentStatus)
    }

    @Test
    fun `GIVEN sale not found WHEN execute THEN throw exception`() {
        // GIVEN
        whenever(saleRepository.findByPaymentCode("INVALID"))
            .thenReturn(null)

        // WHEN
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute("INVALID", PaymentStatus.APPROVED)
        }

        // THEN
        assertEquals("Sale not found", exception.message)

        verify(vehicleRepository, never()).findById(any())
        verify(saleRepository, never()).save(any())
    }

    @Test
    fun `GIVEN vehicle not found WHEN execute THEN throw exception`() {
        // GIVEN
        val sale = createSale(
            paymentCode = "PAY999",
            vehicleId = 99L
        )

        whenever(saleRepository.findByPaymentCode("PAY999"))
            .thenReturn(sale)

        whenever(vehicleRepository.findById(99L))
            .thenReturn(null)

        // WHEN
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute("PAY999", PaymentStatus.APPROVED)
        }

        // THEN
        assertEquals("Vehicle not found", exception.message)

        verify(saleRepository, never()).save(any())
        verify(vehicleRepository, never()).save(any())
    }

    private fun createSale(
        paymentCode: String,
        vehicleId: Long = 1L
    ) = Sale(
        id = 1L,
        vehicleId = vehicleId,
        cpf = "12345678900",
        paymentCode = paymentCode,
        paymentStatus = PaymentStatus.PENDING
    )

    private fun createVehicle(
        status: VehicleStatus
    ) = Vehicle(
        id = 1L,
        brand = "Toyota",
        model = "Corolla",
        year = 2024,
        color = "White",
        price = BigDecimal("100000.00"),
        status = status
    )
}
