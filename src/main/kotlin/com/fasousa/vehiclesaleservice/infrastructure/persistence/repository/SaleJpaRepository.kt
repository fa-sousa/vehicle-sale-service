package com.fasousa.vehiclesaleservice.infrastructure.persistence.repository

import com.fasousa.vehiclesaleservice.infrastructure.persistence.entity.SaleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SaleJpaRepository : JpaRepository<SaleEntity, Long> {

    fun findByPaymentCode(paymentCode: String): SaleEntity?
}
