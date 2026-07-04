package com.fasousa.vehiclesaleservice.application.service

import com.fasousa.vehiclesaleservice.application.usecase.UpdateVehicleUseCase
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UpdateVehicleUseCaseImpl(
    private val vehicleRepository: VehicleRepository
) : UpdateVehicleUseCase {

    override fun execute(
        id: Long,
        vehicle: Vehicle
    ): Vehicle {

        val existingVehicle =
            vehicleRepository.findById(id)
                ?: throw IllegalArgumentException("Vehicle not found")

        if (existingVehicle.status != VehicleStatus.AVAILABLE) {
            throw IllegalStateException(
                "Only available vehicles can be updated"
            )
        }

        val updatedVehicle =
            existingVehicle.copy(
                brand = vehicle.brand,
                model = vehicle.model,
                year = vehicle.year,
                color = vehicle.color,
                price = vehicle.price,
                updatedAt = LocalDateTime.now()
            )

        return vehicleRepository.save(updatedVehicle)
    }
}
