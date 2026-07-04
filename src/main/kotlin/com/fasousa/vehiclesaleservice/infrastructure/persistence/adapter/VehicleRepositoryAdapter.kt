package com.fasousa.vehiclesaleservice.infrastructure.persistence.adapter

import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import com.fasousa.vehiclesaleservice.infrastructure.mapper.VehicleMapper
import com.fasousa.vehiclesaleservice.infrastructure.repository.VehicleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class VehicleRepositoryAdapter(
    private val vehicleJpaRepository: VehicleJpaRepository
) : VehicleRepository {

    override fun save(vehicle: Vehicle): Vehicle {
        val entity = VehicleMapper.toEntity(vehicle)
        return VehicleMapper.toDomain(
            vehicleJpaRepository.save(entity)
        )
    }

    override fun findById(id: Long): Vehicle? {
        return vehicleJpaRepository.findById(id)
            .map(VehicleMapper::toDomain)
            .orElse(null)
    }

    override fun findByStatus(status: VehicleStatus): List<Vehicle> {
        return vehicleJpaRepository.findByStatus(status)
            .map(VehicleMapper::toDomain)
    }
}
