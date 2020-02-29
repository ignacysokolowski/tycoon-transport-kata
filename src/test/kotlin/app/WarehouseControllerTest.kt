package app

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.app.WarehouseController
import tycoon.transport.domain.ShipmentId
import tycoon.transport.domain.Transport

class WarehouseControllerTest {

    @Test fun `drops off shipment from a transport that arrived`() {
        val controller = WarehouseController()
        val transport = TransportSpy()
        controller.transportArrived(transport)
        assertThat(transport.shipmentDroppedOff, equalTo(true))
    }
}

class TransportSpy : Transport {
    var shipmentDroppedOff = false

    override fun dropOff(): ShipmentId {
        shipmentDroppedOff = true
        return ShipmentId("dummy")
    }
}
