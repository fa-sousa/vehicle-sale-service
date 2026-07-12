package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.application.service.ListSoldVehicleUseCaseImpl
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
class ListSoldVehicleUseCaseImplTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var useCase: ListSoldVehicleUseCaseImpl

    @Test
    fun `GIVEN sold vehicles WHEN execute THEN return sold vehicles`() {

        val vehicles = listOf(
            Vehicle(
                id = 1L,
                brand = "BMW",
                model = "320i",
                year = 2024,
                color = "Black",
                price = BigDecimal("200000"),
                status = VehicleStatus.SOLD
            )
        )

        `when`(
            vehicleRepository.findByStatus(VehicleStatus.SOLD)
        ).thenReturn(vehicles)

        val result = useCase.execute()

        assertEquals(1, result.size)
    }
}
