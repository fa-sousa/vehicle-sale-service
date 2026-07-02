package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.domain.model.Vehicle

interface ListSoldVehiclesUseCase {

    fun execute(): List<Vehicle>
}
