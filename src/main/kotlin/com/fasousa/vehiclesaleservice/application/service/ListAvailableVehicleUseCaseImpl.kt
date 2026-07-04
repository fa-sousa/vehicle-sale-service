package com.fasousa.vehiclesaleservice.application.service

import com.fasousa.vehiclesaleservice.application.usecase.ListAvailableVehiclesUseCase
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class ListAvailableVehicleUseCaseImpl(
    private val vehicleRepository: VehicleRepository
) : ListAvailableVehiclesUseCase {

    override fun execute(): List<Vehicle> = vehicleRepository.findByStatus(VehicleStatus.AVAILABLE)
}
