package com.fasousa.vehiclesaleservice.infrastructure.persistence.adapter

import com.fasousa.vehiclesaleservice.domain.model.Sale
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.infrastructure.mapper.SaleMapper
import com.fasousa.vehiclesaleservice.infrastructure.repository.SaleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class SaleRepositoryAdapter(
    private val saleJpaRepository: SaleJpaRepository
) : SaleRepository {

    override fun save(sale: Sale): Sale {
        val entity = SaleMapper.toEntity(sale)

        return SaleMapper.toDomain(
            saleJpaRepository.save(entity)
        )
    }

    override fun findByPaymentCode(paymentCode: String): Sale? {
        return saleJpaRepository.findByPaymentCode(paymentCode)
            ?.let(SaleMapper::toDomain)
    }

    override fun findById(id: Long): Sale? {
        return saleJpaRepository.findById(id)
            .map(SaleMapper::toDomain)
            .orElse(null)
    }
}
