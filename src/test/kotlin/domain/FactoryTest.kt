package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.AllShipmentsPickedUp
import tycoon.transport.domain.Factory
import tycoon.transport.domain.Shipment
import tycoon.transport.domain.ShipmentId
import tycoon.transport.domain.ShipmentNotPickedUp
import tycoon.transport.domain.WarehouseId

class FactoryTest {

    private val factory = Factory()

    @Test fun `has no shipments waiting`() {
        assertThat(factory.hasShipmentsWaiting(), equalTo(false))
    }

    @Test fun `has all shipments delivered if has not collected any yet`() {
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(true))
    }

    @Test fun `has no shipments to be picked up`() {
        assertThrows<AllShipmentsPickedUp> {
            factory.pickUpNextShipment()
        }
    }

    @Test fun `has shipments waiting to be picked up`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A"))
        ))
        assertThat(factory.hasShipmentsWaiting(), equalTo(true))
    }

    @Test fun `shipments waiting to be picked up are not delivered yet`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A"))
        ))
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }

    @Test fun `can be notified about delivery`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A"))
        ))
        factory.shipmentDelivered(factory.pickUpNextShipment())
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(true))
    }

    @Test fun `delivering a single shipment does not deliver all`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A")),
            Shipment(ShipmentId("2"), WarehouseId("B"))
        ))
        factory.pickUpNextShipment()
        factory.shipmentDelivered(factory.pickUpNextShipment())
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }

    @Test fun `can not be notified about delivery of a shipment that was not picked up`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A"))
        ))
        factory.pickUpNextShipment()
        assertThrows<ShipmentNotPickedUp> {
            factory.shipmentDelivered(Shipment(ShipmentId("1"), WarehouseId("B")))
        }
    }

    @Test fun `provides next shipment to pick up`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A")),
            Shipment(ShipmentId("2"), WarehouseId("B"))
        ))
        assertThat(factory.pickUpNextShipment(), equalTo(Shipment(ShipmentId("1"), WarehouseId("A"))))
        assertThat(factory.pickUpNextShipment(), equalTo(Shipment(ShipmentId("2"), WarehouseId("B"))))
    }

    @Test fun `shipments picked up are not delivered yet`() {
        factory.collectShipments(listOf(
            Shipment(ShipmentId("1"), WarehouseId("A"))
        ))
        factory.pickUpNextShipment()
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }
}
