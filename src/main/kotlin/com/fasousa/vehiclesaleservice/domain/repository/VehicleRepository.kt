package com.fasousa.vehiclesaleservice.domain.repository

import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus

interface VehicleRepository {

    fun save(vehicle: Vehicle): Vehicle

    fun findById(id: Long): Vehicle?

    fun findByStatus(status: VehicleStatus): List<Vehicle>
}
