package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.UpdateVehicleUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
class UpdateVehicleUseCaseImplTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var useCase: UpdateVehicleUseCaseImpl

    @Test
    fun `GIVEN available vehicle WHEN update THEN return updated vehicle`() {

        val existingVehicle =
            Vehicle(
                id = 1L,
                brand = "Toyota",
                model = "Corolla",
                year = 2024,
                color = "White",
                price = BigDecimal("100000"),
                status = VehicleStatus.AVAILABLE
            )

        val updateRequest =
            Vehicle(
                id = 1L,
                brand = "Honda",
                model = "Civic",
                year = 2025,
                color = "Black",
                price = BigDecimal("120000")
            )

        whenever(vehicleRepository.findById(1L))
            .thenReturn(existingVehicle)

        whenever(vehicleRepository.save(any()))
            .thenAnswer { it.arguments[0] as Vehicle }

        val result =
            useCase.execute(
                1L,
                updateRequest
            )

        assertEquals("Honda", result.brand)
        assertEquals("Civic", result.model)

        verify(vehicleRepository).save(any())
    }

    @Test
    fun `GIVEN vehicle not found WHEN update THEN throw exception`() {

        whenever(vehicleRepository.findById(1L))
            .thenReturn(null)

        assertThrows<IllegalArgumentException> {

            useCase.execute(
                1L,
                Vehicle(
                    brand = "Honda",
                    model = "Civic",
                    year = 2025,
                    color = "Black",
                    price = BigDecimal("120000")
                )
            )
        }
    }

    @Test
    fun `GIVEN sold vehicle WHEN update THEN throw exception`() {

        val existingVehicle =
            Vehicle(
                id = 1L,
                brand = "Toyota",
                model = "Corolla",
                year = 2024,
                color = "White",
                price = BigDecimal("100000"),
                status = VehicleStatus.SOLD
            )

        whenever(vehicleRepository.findById(1L))
            .thenReturn(existingVehicle)

        assertThrows<IllegalStateException> {

            useCase.execute(
                1L,
                Vehicle(
                    brand = "Honda",
                    model = "Civic",
                    year = 2025,
                    color = "Black",
                    price = BigDecimal("120000")
                )
            )
        }
    }
}
