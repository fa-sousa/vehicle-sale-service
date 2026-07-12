package com.fasousa.vehiclesaleservice.domain.exception

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DomainExceptionTest {

    @Test
    fun `GIVEN VehicleNotFoundException WHEN created THEN should create correct message`() {

        val exception = VehicleNotFoundException(1L)

        assertEquals(
            "Vehicle with id 1 not found",
            exception.message
        )
    }

    @Test
    fun `GIVEN VehicleNotAvailableException WHEN created THEN should create correct message`() {

        val exception = VehicleNotAvailableException(2L)

        assertEquals(
            "Vehicle with id 2 is not available for purchase",
            exception.message
        )
    }

    @Test
    fun `GIVEN VehicleAlreadySoldException WHEN created THEN should create correct message`() {

        val exception = VehicleAlreadySoldException(3L)

        assertEquals(
            "Vehicle with id 3 is already sold",
            exception.message
        )
    }
}
