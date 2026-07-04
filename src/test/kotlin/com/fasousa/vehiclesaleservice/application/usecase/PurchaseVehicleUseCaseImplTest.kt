package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.PurchaseVehicleUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.*
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.Assertions.*
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
class PurchaseVehicleUseCaseImplTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @Mock
    lateinit var saleRepository: SaleRepository

    @InjectMocks
    lateinit var useCase: PurchaseVehicleUseCaseImpl

    @Test
    fun `GIVEN available vehicle WHEN purchase THEN create sale`() {

        val vehicle = Vehicle(
            id = 1L,
            brand = "Toyota",
            model = "Corolla",
            year = 2024,
            color = "White",
            price = BigDecimal("100000"),
            status = VehicleStatus.AVAILABLE
        )

        `when`(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        `when`(saleRepository.save(any()))
            .thenAnswer { it.arguments[0] }

        val sale = useCase.execute(1L, "12345678901")

        assertEquals("12345678901", sale.cpf)

        verify(vehicleRepository).save(any())
        verify(saleRepository).save(any())
    }

    @Test
    fun `GIVEN vehicle not found WHEN purchase THEN throw exception`() {

        `when`(vehicleRepository.findById(1L))
            .thenReturn(null)

        assertThrows<IllegalArgumentException> {
            useCase.execute(1L, "12345678901")
        }
    }

    @Test
    fun `GIVEN sold vehicle WHEN purchase THEN throw exception`() {

        val vehicle = Vehicle(
            id = 1L,
            brand = "Toyota",
            model = "Corolla",
            year = 2024,
            color = "White",
            price = BigDecimal("100000"),
            status = VehicleStatus.SOLD
        )

        `when`(vehicleRepository.findById(1L))
            .thenReturn(vehicle)

        assertThrows<IllegalStateException> {
            useCase.execute(1L, "12345678901")
        }
    }
}
