package com.fasousa.vehiclesaleservice.application.service

import com.fasousa.vehiclesaleservice.application.usecase.ProcessPaymentWebhookUseCase
import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.domain.repository.SaleRepository
import com.fasousa.vehiclesaleservice.domain.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class ProcessPaymentWebhookUseCaseImpl(
    private val saleRepository: SaleRepository,
    private val vehicleRepository: VehicleRepository
) : ProcessPaymentWebhookUseCase {

    override fun execute(
        paymentCode: String,
        status: PaymentStatus
    ) {

        val sale =
            saleRepository.findByPaymentCode(paymentCode)
                ?: throw IllegalArgumentException("Sale not found")

        val vehicle =
            vehicleRepository.findById(sale.vehicleId)
                ?: throw IllegalArgumentException("Vehicle not found")

        saleRepository.save(
            sale.copy(
                paymentStatus = status
            )
        )

        when (status) {

            PaymentStatus.APPROVED -> {
                vehicleRepository.save(
                    vehicle.copy(
                        status = VehicleStatus.SOLD
                    )
                )
            }

            PaymentStatus.CANCELLED -> {
                vehicleRepository.save(
                    vehicle.copy(
                        status = VehicleStatus.AVAILABLE
                    )
                )
            }

            PaymentStatus.PENDING -> {
                // no action
            }
        }
    }
}
