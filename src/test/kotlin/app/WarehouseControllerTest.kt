package app

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.app.WarehouseController
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.DeliveryListener
import tycoon.transport.domain.Transport

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
    var goesBack = false

    override fun dropOff() = CargoId("dummy")

    override fun goBack() {
        goesBack = true
    }
}
