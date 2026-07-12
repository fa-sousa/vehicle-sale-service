package com.fasousa.vehiclesaleservice.presentation

import com.fasousa.vehiclesaleservice.application.usecase.CreateVehicleUseCase
import com.fasousa.vehiclesaleservice.application.usecase.ListAvailableVehiclesUseCase
import com.fasousa.vehiclesaleservice.application.usecase.ListSoldVehiclesUseCase
import com.fasousa.vehiclesaleservice.application.usecase.PurchaseVehicleUseCase
import com.fasousa.vehiclesaleservice.application.usecase.UpdateVehicleUseCase
import com.fasousa.vehiclesaleservice.domain.model.PaymentStatus
import com.fasousa.vehiclesaleservice.domain.model.Sale
import com.fasousa.vehiclesaleservice.domain.model.Vehicle
import com.fasousa.vehiclesaleservice.domain.model.VehicleStatus
import com.fasousa.vehiclesaleservice.presentation.request.CreateVehicleRequest
import com.fasousa.vehiclesaleservice.presentation.request.PurchaseVehicleRequest
import com.fasousa.vehiclesaleservice.presentation.request.UpdateVehicleRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class VehicleControllerTest {

    @Mock
    lateinit var createVehicleUseCase: CreateVehicleUseCase

    @Mock
    lateinit var listAvailableVehiclesUseCase: ListAvailableVehiclesUseCase

    @Mock
    lateinit var listSoldVehiclesUseCase: ListSoldVehiclesUseCase

    @Mock
    lateinit var updateVehicleUseCase: UpdateVehicleUseCase

    @Mock
    lateinit var purchaseVehicleUseCase: PurchaseVehicleUseCase

    @InjectMocks
    lateinit var controller: VehicleController

    @Test
    fun `GIVEN vehicle request WHEN create THEN execute create use case`() {
        // GIVEN
        val request = CreateVehicleRequest(
            brand = "Honda",
            model = "Civic",
            year = 2024,
            color = "Prata",
            price = BigDecimal("125000.00")
        )

        val vehicleCaptor = argumentCaptor<Vehicle>()

        // WHEN
        controller.create(request)

        // THEN
        verify(createVehicleUseCase).execute(vehicleCaptor.capture())

        val vehicle = vehicleCaptor.firstValue

        assertEquals("Honda", vehicle.brand)
        assertEquals("Civic", vehicle.model)
        assertEquals(2024, vehicle.year)
        assertEquals("Prata", vehicle.color)
        assertEquals(BigDecimal("125000.00"), vehicle.price)
        assertEquals(VehicleStatus.AVAILABLE, vehicle.status)
    }

    @Test
    fun `GIVEN available vehicles WHEN list available THEN return responses`() {
        // GIVEN
        val vehicles = listOf(
            createVehicle(
                id = 1L,
                price = BigDecimal("80000.00"),
                status = VehicleStatus.AVAILABLE
            ),
            createVehicle(
                id = 2L,
                price = BigDecimal("100000.00"),
                status = VehicleStatus.AVAILABLE
            )
        )

        whenever(listAvailableVehiclesUseCase.execute())
            .thenReturn(vehicles)

        // WHEN
        val response = controller.listAvailable()

        // THEN
        assertEquals(2, response.size)
        assertEquals(1L, response[0].id)
        assertEquals(BigDecimal("80000.00"), response[0].price)
        assertEquals(VehicleStatus.AVAILABLE, response[0].status)

        verify(listAvailableVehiclesUseCase).execute()
    }

    @Test
    fun `GIVEN sold vehicles WHEN list sold THEN return responses`() {
        // GIVEN
        val vehicles = listOf(
            createVehicle(
                id = 1L,
                price = BigDecimal("90000.00"),
                status = VehicleStatus.SOLD
            )
        )

        whenever(listSoldVehiclesUseCase.execute())
            .thenReturn(vehicles)

        // WHEN
        val response = controller.listSold()

        // THEN
        assertEquals(1, response.size)
        assertEquals(1L, response[0].id)
        assertEquals(VehicleStatus.SOLD, response[0].status)

        verify(listSoldVehiclesUseCase).execute()
    }

    @Test
    fun `GIVEN update request WHEN update THEN execute update use case`() {
        // GIVEN
        val request = UpdateVehicleRequest(
            brand = "Toyota",
            model = "Corolla",
            year = 2025,
            color = "Preto",
            price = BigDecimal("140000.00")
        )

        val vehicleCaptor = argumentCaptor<Vehicle>()

        // WHEN
        controller.update(
            id = 10L,
            req = request
        )

        // THEN
        verify(updateVehicleUseCase).execute(
            org.mockito.kotlin.eq(10L),
            vehicleCaptor.capture()
        )

        val vehicle = vehicleCaptor.firstValue

        assertEquals(10L, vehicle.id)
        assertEquals("Toyota", vehicle.brand)
        assertEquals("Corolla", vehicle.model)
        assertEquals(VehicleStatus.AVAILABLE, vehicle.status)
    }

    @Test
    fun `GIVEN purchase request WHEN purchase THEN return sale response`() {
        // GIVEN
        val saleDate = LocalDateTime.now()

        val sale = Sale(
            id = 1L,
            vehicleId = 5L,
            cpf = "12345678900",
            saleDate = saleDate,
            paymentCode = "PAY-123",
            paymentStatus = PaymentStatus.PENDING
        )

        whenever(
            purchaseVehicleUseCase.execute(
                vehicleId = 5L,
                cpf = "12345678900"
            )
        ).thenReturn(sale)

        val request = PurchaseVehicleRequest(
            cpf = "12345678900",
            vehicleId = 5L,
        )

        // WHEN
        val response = controller.purchase(
            id = 5L,
            req = request
        )

        // THEN
        assertEquals(1L, response.id)
        assertEquals(5L, response.vehicleId)
        assertEquals("12345678900", response.cpf)
        assertEquals("PAY-123", response.paymentCode)
        assertEquals(PaymentStatus.PENDING, response.paymentStatus)
        assertEquals(saleDate, response.saleDate)

        verify(purchaseVehicleUseCase).execute(
            vehicleId = 5L,
            cpf = "12345678900"
        )
    }

    private fun createVehicle(
        id: Long,
        price: BigDecimal,
        status: VehicleStatus
    ) = Vehicle(
        id = id,
        brand = "Honda",
        model = "Civic",
        year = 2024,
        color = "Prata",
        price = price,
        status = status
    )
}
