package com.fasousa.vehiclesaleservice.application.usecase

import com.fasousa.vehiclesaleservice.domain.model.Sale

interface PurchaseVehicleUseCase {

    fun execute(vehicleId: Long, cpf: String): Sale
}
