package com.fasousa.vehiclesaleservice.infrastructure.entity

import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
data class SaleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val vehicleId: Long,

    @Column(nullable = false)
    val cpf: String,

    @Column(nullable = false)
    val paymentCode: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val paymentStatus: PaymentStatus,

    @Column(nullable = false)
    val saleDate: LocalDateTime,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    val updatedAt: LocalDateTime
)
