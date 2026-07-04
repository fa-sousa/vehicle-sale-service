package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.CreateVehicleUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import java.math.BigDecimal
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CreateVehicleUseCaseImplTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var useCase: CreateVehicleUseCaseImpl

    @Test
    fun `should create vehicle`() {

        val vehicle = Vehicle(
            brand = "Toyota",
            model = "Corolla",
            year = 2024,
            color = "White",
            price = BigDecimal("100000")
        )

        useCase.execute(vehicle)

        verify(vehicleRepository).save(vehicle)
    }
}
