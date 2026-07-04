package com.fasousa.vehiclesaleservice.infrastructure.mapper

import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.infrastructure.entity.VehicleEntity

object VehicleMapper {

    fun toDomain(entity: VehicleEntity): Vehicle =
        Vehicle(
            id = entity.id,
            brand = entity.brand,
            model = entity.model,
            year = entity.year,
            color = entity.color,
            price = entity.price,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )

    fun toEntity(domain: Vehicle): VehicleEntity =
        VehicleEntity(
            id = domain.id,
            brand = domain.brand,
            model = domain.model,
            year = domain.year,
            color = domain.color,
            price = domain.price,
            status = domain.status,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
}
