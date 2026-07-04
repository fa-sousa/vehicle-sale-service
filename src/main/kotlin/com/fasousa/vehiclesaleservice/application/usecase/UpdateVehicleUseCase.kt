package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.domain.model.Vehicle

interface UpdateVehicleUseCase {

    fun execute(
        id: Long,
        vehicle: Vehicle
    ): Vehicle
}
