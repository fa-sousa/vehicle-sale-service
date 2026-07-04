package com.fasousa.vehiclesaleservice.domain.exception

class VehicleAlreadySoldException(
    id: Long
) : RuntimeException("Vehicle with id $id is already sold")