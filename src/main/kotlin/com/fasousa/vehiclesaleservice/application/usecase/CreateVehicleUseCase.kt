package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.domain.model.Vehicle

interface CreateVehicleUseCase {
    fun execute(vehicle: Vehicle): Vehicle
}
