package com.fasousa.vehiclesaleservice.application.service

import com.fasousa.vehiclesaleservice.application.usecase.ListSoldVehiclesUseCase
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class ListSoldVehicleUseCaseImpl(
    private val vehicleRepository: VehicleRepository
) : ListSoldVehiclesUseCase {

    override fun execute(): List<Vehicle> = vehicleRepository.findByStatus(VehicleStatus.SOLD)
}
