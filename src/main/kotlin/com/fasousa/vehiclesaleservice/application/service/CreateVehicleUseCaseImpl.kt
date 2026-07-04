package com.fasousa.vehiclesaleservice.application.service

import com.fasousa.vehiclesaleservice.application.usecase.CreateVehicleUseCase
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository

class CreateVehicleUseCaseImpl(
    private val vehicleRepository: VehicleRepository
) : CreateVehicleUseCase {

    override fun execute(vehicle: Vehicle) {
        vehicleRepository.save(vehicle)
    }
}
