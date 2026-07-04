package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.ListAvailableVehicleUseCaseImpl
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class ListAvailableVehicleUseCaseImplTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var useCase: ListAvailableVehicleUseCaseImpl

    @Test
    fun `GIVEN available vehicles WHEN execute THEN return available vehicles`() {

        val vehicles = listOf(
            Vehicle(
                id = 1L,
                brand = "Toyota",
                model = "Corolla",
                year = 2024,
                color = "White",
                price = BigDecimal("100000"),
                status = VehicleStatus.AVAILABLE
            )
        )

        `when`(
            vehicleRepository.findByStatus(VehicleStatus.AVAILABLE)
        ).thenReturn(vehicles)

        val result = useCase.execute()

        assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN no available vehicles WHEN execute THEN return empty list`() {

        `when`(
            vehicleRepository.findByStatus(VehicleStatus.AVAILABLE)
        ).thenReturn(emptyList())

        val result = useCase.execute()

        assertEquals(0, result.size)
    }
}
