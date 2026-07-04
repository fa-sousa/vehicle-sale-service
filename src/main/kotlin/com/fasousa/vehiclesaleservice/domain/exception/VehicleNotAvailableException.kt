package com.fasousa.vehiclesaleservice.domain.exception

class VehicleNotAvailableException(
    id: Long
) : RuntimeException("Vehicle with id $id is not available for purchase")