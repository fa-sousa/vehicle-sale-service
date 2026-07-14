package com.fasousa.vehiclesaleservice.presentation

import com.fasousa.vehiclesaleservice.domain.exception.VehicleAlreadySoldException
import com.fasousa.vehiclesaleservice.domain.exception.VehicleNotAvailableException
import com.fasousa.vehiclesaleservice.domain.exception.VehicleNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun `should handle vehicle not found`() {
        val response = handler.handleNotFound(
            VehicleNotFoundException(1L)
        )

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNotNull(response.body?.get("error"))
    }

    @Test
    fun `should handle vehicle already sold`() {
        val response = handler.handleAlreadySold(
            VehicleAlreadySoldException(1L)
        )

        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertNotNull(response.body?.get("error"))
    }

    @Test
    fun `should handle vehicle not available`() {
        val response = handler.handleNotAvailable(
            VehicleNotAvailableException(1L)
        )

        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertNotNull(response.body?.get("error"))
    }
}
