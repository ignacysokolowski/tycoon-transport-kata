package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Factory
import tycoon.transport.domain.Shipment
import tycoon.transport.domain.WarehouseId

class FactoryTest {

    private val factory = Factory()

    @Test fun `has no shipments waiting`() {
        assertThat(factory.shipmentsWaiting(), equalTo(emptyList()))
    }

    @Test fun `has no shipments to be picked up`() {
        assertThrows<RuntimeException> {
            factory.pickUpNextShipment()
        }
    }

    @Test fun `collects shipments`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A")),
            Shipment(WarehouseId("B"))
        ))
        assertThat(factory.shipmentsWaiting(), equalTo(listOf(
            Shipment(WarehouseId("A")),
            Shipment(WarehouseId("B"))
        )))
    }
}
