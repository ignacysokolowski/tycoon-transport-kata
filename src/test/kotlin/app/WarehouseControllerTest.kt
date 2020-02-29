package app

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.app.WarehouseController
import tycoon.transport.domain.DeliveryListener
import tycoon.transport.domain.ShipmentId
import tycoon.transport.domain.Transport

class WarehouseControllerTest {

    private val deliveryListener = DeliverySpy()
    private val controller = WarehouseController(deliveryListener)

    @Test fun `drops off shipment from a transport that arrived and notifies about delivery`() {
        controller.transportArrived(TransportStub())
        assertThat(deliveryListener.shipmentsDelivered, equalTo(listOf(ShipmentId("dummy"))))
    }

    @Test fun `directs transports back to the origin`() {
        val transport = TransportStub()
        controller.transportArrived(transport)
        assertThat(transport.goesBack, equalTo(true))
    }
}

class DeliverySpy : DeliveryListener {
    val shipmentsDelivered = mutableListOf<ShipmentId>()

    override fun shipmentDelivered(shipmentId: ShipmentId) {
        shipmentsDelivered.add(shipmentId)
    }
}

class TransportStub : Transport {
    var goesBack = false

    override fun dropOff() = ShipmentId("dummy")

    override fun goBack() {
        goesBack = true
    }
}
