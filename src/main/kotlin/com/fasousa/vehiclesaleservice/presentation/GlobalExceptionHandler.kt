package com.fasousa.vehiclesaleservice.presentation

import com.fasousa.vehiclesaleservice.domain.exception.VehicleAlreadySoldException
import com.fasousa.vehiclesaleservice.domain.exception.VehicleNotAvailableException
import com.fasousa.vehiclesaleservice.domain.exception.VehicleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(VehicleNotFoundException::class)
    fun handleNotFound(ex: VehicleNotFoundException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (ex.message ?: "Vehicle not found")))
    }

    @ExceptionHandler(VehicleAlreadySoldException::class)
    fun handleAlreadySold(ex: VehicleAlreadySoldException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (ex.message ?: "Vehicle already sold")))
    }

    @ExceptionHandler(VehicleNotAvailableException::class)
    fun handleNotAvailable(ex: VehicleNotAvailableException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (ex.message ?: "Vehicle not available")))
    }
}
