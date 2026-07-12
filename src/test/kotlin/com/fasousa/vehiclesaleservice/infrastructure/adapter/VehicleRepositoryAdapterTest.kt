package com.fasousa.vehiclesaleservice.infrastructure.adapter

import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.infrastructure.entity.VehicleEntity
import com.fasousa.vehiclesaleservice.infrastructure.persistence.adapter.VehicleRepositoryAdapter
import com.fasousa.vehiclesaleservice.infrastructure.repository.VehicleJpaRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertNull

@ExtendWith(MockitoExtension::class)
class VehicleRepositoryAdapterTest {

    @Mock
    lateinit var vehicleJpaRepository: VehicleJpaRepository

    @Test
    fun `GIVEN vehicle WHEN save THEN return saved vehicle`() {
        // GIVEN
        val adapter = VehicleRepositoryAdapter(vehicleJpaRepository)

        val vehicle = createVehicle(
            id = null,
            status = VehicleStatus.AVAILABLE
        )

        val savedEntity = createVehicleEntity(
            id = 1L,
            status = VehicleStatus.AVAILABLE
        )

        val entityCaptor = argumentCaptor<VehicleEntity>()

        whenever(vehicleJpaRepository.save(entityCaptor.capture()))
            .thenReturn(savedEntity)

        // WHEN
        val result = adapter.save(vehicle)

        // THEN
        verify(vehicleJpaRepository).save(entityCaptor.firstValue)

        assertEquals(1L, result.id)
        assertEquals("Toyota", result.brand)
        assertEquals("Corolla", result.model)
        assertEquals(VehicleStatus.AVAILABLE, result.status)

        assertEquals("Toyota", entityCaptor.firstValue.brand)
        assertEquals("Corolla", entityCaptor.firstValue.model)
    }

    @Test
    fun `GIVEN existing id WHEN findById THEN return vehicle`() {
        // GIVEN
        val adapter = VehicleRepositoryAdapter(vehicleJpaRepository)

        val entity = createVehicleEntity(
            id = 1L,
            status = VehicleStatus.AVAILABLE
        )

        whenever(vehicleJpaRepository.findById(1L))
            .thenReturn(Optional.of(entity))

        // WHEN
        val result = adapter.findById(1L)

        // THEN
        assertEquals(1L, result?.id)
        assertEquals("Toyota", result?.brand)
        assertEquals(VehicleStatus.AVAILABLE, result?.status)
    }

    @Test
    fun `GIVEN unknown id WHEN findById THEN return null`() {
        // GIVEN
        val adapter = VehicleRepositoryAdapter(vehicleJpaRepository)

        whenever(vehicleJpaRepository.findById(99L))
            .thenReturn(Optional.empty())

        // WHEN
        val result = adapter.findById(99L)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN status WHEN findByStatus THEN return mapped vehicles`() {
        // GIVEN
        val adapter = VehicleRepositoryAdapter(vehicleJpaRepository)

        val entities = listOf(
            createVehicleEntity(
                id = 1L,
                status = VehicleStatus.AVAILABLE
            ),
            createVehicleEntity(
                id = 2L,
                status = VehicleStatus.AVAILABLE
            )
        )

        whenever(vehicleJpaRepository.findByStatus(VehicleStatus.AVAILABLE))
            .thenReturn(entities)

        // WHEN
        val result = adapter.findByStatus(VehicleStatus.AVAILABLE)

        // THEN
        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertEquals(2L, result[1].id)

        verify(vehicleJpaRepository)
            .findByStatus(VehicleStatus.AVAILABLE)
    }

    private fun createVehicle(
        id: Long?,
        status: VehicleStatus
    ) = Vehicle(
        id = id,
        brand = "Toyota",
        model = "Corolla",
        year = 2024,
        color = "White",
        price = BigDecimal("100000.00"),
        status = status
    )

    private fun createVehicleEntity(
        id: Long?,
        status: VehicleStatus
    ) = VehicleEntity(
        id = id,
        brand = "Toyota",
        model = "Corolla",
        year = 2024,
        color = "White",
        price = BigDecimal("100000.00"),
        status = status,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}
