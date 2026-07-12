package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.domain.model.Vehicle

interface ListAvailableVehiclesUseCase {

    fun execute(): List<Vehicle>
}
