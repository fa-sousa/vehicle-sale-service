package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.PurchaseVehicleUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import com.fasousa.vehiclesaleservice.domain.model.Sale
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class PurchaseVehicleUseCaseImplTest {

    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var saleRepository: SaleRepository
    private lateinit var useCase: PurchaseVehicleUseCaseImpl

    @BeforeEach
    fun setUp() {
        vehicleRepository = mock()
        saleRepository = mock()

        useCase = PurchaseVehicleUseCaseImpl(
            vehicleRepository = vehicleRepository,
            saleRepository = saleRepository
        )
    }

    @Test
    fun `should purchase an available vehicle`() {
        val vehicle = Vehicle(
            id = 1L,
            brand = "Honda",
            model = "Civic",
            year = 2024,
            color = "Prata",
            price = BigDecimal("125000.00"),
            status = VehicleStatus.AVAILABLE
        )

        val savedSale = Sale(
            id = 10L,
            vehicleId = 1L,
            cpf = "12345678900",
            paymentCode = "payment-code",
            paymentStatus = PaymentStatus.PENDING
        )

        whenever(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        whenever(vehicleRepository.save(any()))
            .thenAnswer { it.arguments[0] as Vehicle }

        whenever(saleRepository.save(any()))
            .thenReturn(savedSale)

        val result = useCase.execute(
            vehicleId = 1L,
            cpf = "12345678900"
        )

        assertEquals(10L, result.id)
        assertEquals(1L, result.vehicleId)
        assertEquals(PaymentStatus.PENDING, result.paymentStatus)

        val vehicleCaptor = argumentCaptor<Vehicle>()
        verify(vehicleRepository).save(vehicleCaptor.capture())

        assertEquals(
            VehicleStatus.PENDING_PAYMENT,
            vehicleCaptor.firstValue.status
        )

        verify(saleRepository).save(any())
    }

    @Test
    fun `should throw exception when vehicle does not exist`() {
        whenever(vehicleRepository.findById(99L))
            .thenReturn(null)

        val exception = assertThrows(
            IllegalArgumentException::class.java
        ) {
            useCase.execute(
                vehicleId = 99L,
                cpf = "12345678900"
            )
        }

        assertEquals("Vehicle not found", exception.message)
    }

    @Test
    fun `should throw exception when vehicle is unavailable`() {
        val vehicle = Vehicle(
            id = 2L,
            brand = "Toyota",
            model = "Corolla",
            year = 2023,
            color = "Preto",
            price = BigDecimal("100000.00"),
            status = VehicleStatus.PENDING_PAYMENT
        )

        whenever(vehicleRepository.findById(2L))
            .thenReturn(vehicle)

        val exception = assertThrows(
            IllegalStateException::class.java
        ) {
            useCase.execute(
                vehicleId = 2L,
                cpf = "12345678900"
            )
        }

        assertEquals("Vehicle unavailable", exception.message)
    }
}
