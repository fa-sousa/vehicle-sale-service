package com.fasousa.vehiclesaleservice.domain.exception

class VehicleNotFoundException(
    id: Long
) : RuntimeException("Vehicle with id $id not found")