package com.fasousa.vehiclesaleservice.infrastructure.entity

import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "vehicles")
data class VehicleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val brand: String,

    @Column(nullable = false)
    val model: String,

    @Column(nullable = false)
    val year: Int,

    @Column(nullable = false)
    val color: String,

    @Column(nullable = false)
    val price: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: VehicleStatus,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    val updatedAt: LocalDateTime
)
