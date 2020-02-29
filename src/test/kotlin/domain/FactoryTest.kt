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
            Shipment(WarehouseId("A"), ShipmentId("1"))
        ))
        assertThat(factory.hasShipmentsWaiting(), equalTo(true))
    }

    @Test fun `shipments waiting to be picked up are not delivered yet`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A"), ShipmentId("1"))
        ))
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }

    @Test fun `can be notified about delivery`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A"), ShipmentId("1"))
        ))
        factory.shipmentDelivered(factory.pickUpNextShipment())
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(true))
    }

    @Test fun `delivering a single shipment does not deliver all`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A"), ShipmentId("1")),
            Shipment(WarehouseId("B"), ShipmentId("2"))
        ))
        factory.pickUpNextShipment()
        factory.shipmentDelivered(factory.pickUpNextShipment())
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }

    @Test fun `can not be notified about delivery of a shipment that was not picked up`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A"), ShipmentId("1"))
        ))
        factory.pickUpNextShipment()
        assertThrows<ShipmentNotPickedUp> {
            factory.shipmentDelivered(Shipment(WarehouseId("B"), ShipmentId("1")))
        }
    }

    @Test fun `provides next shipment to pick up`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A"), ShipmentId("1")),
            Shipment(WarehouseId("B"), ShipmentId("2"))
        ))
        assertThat(factory.pickUpNextShipment(), equalTo(Shipment(WarehouseId("A"), ShipmentId("1"))))
        assertThat(factory.pickUpNextShipment(), equalTo(Shipment(WarehouseId("B"), ShipmentId("2"))))
    }

    @Test fun `shipments picked up are not delivered yet`() {
        factory.collectShipments(listOf(
            Shipment(WarehouseId("A"), ShipmentId("1"))
        ))
        factory.pickUpNextShipment()
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }
}
