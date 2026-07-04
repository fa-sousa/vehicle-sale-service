package com.fasousa.vehiclesaleservice.application.service

import com.fasousa.vehiclesaleservice.application.usecase.PurchaseVehicleUseCase
import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import com.fasousa.vehiclesaleservice.domain.model.Sale
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PurchaseVehicleUseCaseImpl(
    private val vehicleRepository: VehicleRepository,
    private val saleRepository: SaleRepository
) : PurchaseVehicleUseCase {

    override fun execute(
        vehicleId: Long,
        cpf: String
    ): Sale {

        val vehicle =
            vehicleRepository.findById(vehicleId)
                ?: throw IllegalArgumentException("Vehicle not found")

        if (vehicle.status != VehicleStatus.AVAILABLE) {
            throw IllegalStateException("Vehicle unavailable")
        }

        vehicleRepository.save(
            vehicle.copy(
                status = VehicleStatus.PENDING_PAYMENT
            )
        )

        val sale = Sale(
            vehicleId = vehicleId,
            cpf = cpf,
            paymentCode = UUID.randomUUID().toString(),
            paymentStatus = PaymentStatus.PENDING
        )

        return saleRepository.save(sale)
    }
}
