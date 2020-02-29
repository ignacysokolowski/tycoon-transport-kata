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

    @Test fun `drops off shipment from a transport that arrived`() {
        val transport = TransportSpy()
        controller.transportArrived(transport)
        assertThat(transport.shipmentDroppedOff, equalTo(true))
    }

    @Test fun `notifies about shipment delivery`() {
        controller.transportArrived(TransportSpy())
        assertThat(deliveryListener.shipmentsDelivered, equalTo(listOf(ShipmentId("dummy"))))
    }
}

class DeliverySpy : DeliveryListener {
    val shipmentsDelivered = mutableListOf<ShipmentId>()

    override fun shipmentDelivered(shipmentId: ShipmentId) {
        shipmentsDelivered.add(shipmentId)
    }
}

class TransportSpy : Transport {
    var shipmentDroppedOff = false

    override fun dropOff(): ShipmentId {
        shipmentDroppedOff = true
        return ShipmentId("dummy")
    }
}
