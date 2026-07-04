package com.fasousa.vehiclesaleservice.domain.repository

import com.fasousa.vehiclesaleservice.domain.model.Sale

interface SaleRepository {

    fun save(sale: Sale): Sale

    fun findByPaymentCode(paymentCode: String): Sale?
}
