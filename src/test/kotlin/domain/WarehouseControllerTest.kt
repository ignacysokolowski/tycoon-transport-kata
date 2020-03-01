package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.DeliveryListener
import tycoon.transport.domain.Transport
import tycoon.transport.domain.Trip
import tycoon.transport.domain.WarehouseController

class WarehouseControllerTest {

    private val deliveryListener = DeliverySpy()
    private val controller = WarehouseController(deliveryListener)

    @Test fun `drops off cargo from a transport that arrived and notifies about delivery`() {
        controller.transportArrived(FakeTransport())
        assertThat(deliveryListener.cargoDelivered, equalTo(listOf(CargoId("dummy"))))
    }

    @Test fun `directs transports back to the origin`() {
        val transport = FakeTransport()
        controller.transportArrived(transport)
        assertThat(transport.goesBack, equalTo(true))
    }
}

class DeliverySpy : DeliveryListener {
    val cargoDelivered = mutableListOf<CargoId>()

    override fun cargoDelivered(cargoId: CargoId) {
        cargoDelivered.add(cargoId)
    }
}

class FakeTransport : Transport {
    var cargoPickedUp: CargoId? = null
    var tripStarted: Trip? = null
    var goesBack = false

    override fun pickUp(cargoId: CargoId) {
        cargoPickedUp = cargoId
    }

    override fun startTrip(trip: Trip) {
        tripStarted = trip
    }

    override fun dropOff() = CargoId("dummy")

    override fun goBack() {
        goesBack = true
    }
}
