package com.fasousa.vehiclesaleservice.infrastructure.mapper

import com.fasousa.vehiclesaleservice.domain.model.Sale
import com.fasousa.vehiclesaleservice.infrastructure.entity.SaleEntity

object SaleMapper {

    fun toDomain(entity: SaleEntity): Sale =
        Sale(
            id = entity.id,
            vehicleId = entity.vehicleId,
            cpf = entity.cpf,
            paymentCode = entity.paymentCode,
            paymentStatus = entity.paymentStatus,
            saleDate = entity.saleDate,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )

    fun toEntity(domain: Sale): SaleEntity =
        SaleEntity(
            id = domain.id,
            vehicleId = domain.vehicleId,
            cpf = domain.cpf,
            paymentCode = domain.paymentCode,
            paymentStatus = domain.paymentStatus,
            saleDate = domain.saleDate,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
}
