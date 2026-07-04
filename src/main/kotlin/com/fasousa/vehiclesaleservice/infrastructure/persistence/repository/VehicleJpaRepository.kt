package com.fasousa.vehiclesaleservice.infrastructure.persistence.repository

import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.infrastructure.persistence.entity.VehicleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleJpaRepository : JpaRepository<VehicleEntity, Long> {

    fun findByStatus(status: VehicleStatus): List<VehicleEntity>
}
