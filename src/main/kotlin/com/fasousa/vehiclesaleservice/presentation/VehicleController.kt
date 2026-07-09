package com.fasousa.vehiclesaleservice.presentation

import com.fasousa.vehiclesaleservice.application.usecase.*
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.presentation.request.CreateVehicleRequest
import com.fasousa.vehiclesaleservice.presentation.request.UpdateVehicleRequest
import com.fasousa.vehiclesaleservice.presentation.request.PurchaseVehicleRequest
import com.fasousa.vehiclesaleservice.presentation.response.VehicleResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vehicles")
class VehicleController(
    private val createVehicleUseCase: CreateVehicleUseCase,
    private val listAvailableVehiclesUseCase: ListAvailableVehiclesUseCase,
    private val listSoldVehiclesUseCase: ListSoldVehiclesUseCase,
    private val updateVehicleUseCase: UpdateVehicleUseCase,
    private val purchaseVehicleUseCase: PurchaseVehicleUseCase
) {

    @GetMapping("/available")
    fun listAvailable(): List<VehicleResponse> {
        return listAvailableVehiclesUseCase.execute().map { v ->
            VehicleResponse(v.id, v.brand, v.model, v.year, v.color, v.price, v.status)
        }
    }

    @GetMapping("/sold")
    fun listSold(): List<VehicleResponse> {
        return listSoldVehiclesUseCase.execute().map { v ->
            VehicleResponse(v.id, v.brand, v.model, v.year, v.color, v.price, v.status)
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req: CreateVehicleRequest) {
        val vehicle = Vehicle(null, req.brand, req.model, req.year, req.color, req.price, VehicleStatus.AVAILABLE)
        createVehicleUseCase.execute(vehicle)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: UpdateVehicleRequest) {
        val vehicle = Vehicle(id, req.brand, req.model, req.year, req.color, req.price, VehicleStatus.AVAILABLE)
        updateVehicleUseCase.execute(id, vehicle)
    }

    @PostMapping("/{id}/purchase")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun purchase(@PathVariable id: Long, @RequestBody req: PurchaseVehicleRequest) {
        purchaseVehicleUseCase.execute(req.vehicleId, req.cpf)
    }
}
