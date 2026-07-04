package com.fasousa.vehiclesaleservice.infrastructure.repository

import com.fasousa.vehiclesaleservice.infrastructure.entity.SaleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SaleJpaRepository : JpaRepository<SaleEntity, Long> {

    fun findByPaymentCode(paymentCode: String): SaleEntity?
}
