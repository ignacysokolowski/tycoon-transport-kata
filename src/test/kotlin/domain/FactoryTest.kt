package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.AllShipmentsPickedUp
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Shipment
import tycoon.transport.domain.ShipmentId
import tycoon.transport.domain.ShipmentNotPickedUp

class FactoryTest {

    private val factory = Factory()

    @Test fun `has all shipments delivered if has not produced any yet`() {
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(true))
    }

    @Test fun `has no shipments to be picked up`() {
        assertThrows<AllShipmentsPickedUp> {
            factory.pickUpNextShipment()
        }
    }

    @Test fun `shipments waiting to be picked up are not delivered yet`() {
        factory.produce(listOf(
            Shipment(ShipmentId("1"), LocationId("A"))
        ))
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }

    @Test fun `can be notified about delivery`() {
        factory.produce(listOf(
            Shipment(ShipmentId("1"), LocationId("A"))
        ))
        factory.shipmentDelivered(factory.pickUpNextShipment().id)
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(true))
    }

    @Test fun `delivering a single shipment does not deliver all`() {
        factory.produce(listOf(
            Shipment(ShipmentId("1"), LocationId("A")),
            Shipment(ShipmentId("2"), LocationId("B"))
        ))
        factory.pickUpNextShipment()
        factory.shipmentDelivered(factory.pickUpNextShipment().id)
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }

    @Test fun `can not be notified about delivery of a shipment that was not picked up`() {
        factory.produce(listOf(
            Shipment(ShipmentId("1"), LocationId("A"))
        ))
        factory.pickUpNextShipment()
        assertThrows<ShipmentNotPickedUp> {
            factory.shipmentDelivered(ShipmentId("2"))
        }
    }

    @Test fun `provides next shipment to pick up`() {
        factory.produce(listOf(
            Shipment(ShipmentId("1"), LocationId("A")),
            Shipment(ShipmentId("2"), LocationId("B"))
        ))
        assertThat(factory.pickUpNextShipment(), equalTo(Shipment(ShipmentId("1"), LocationId("A"))))
        assertThat(factory.pickUpNextShipment(), equalTo(Shipment(ShipmentId("2"), LocationId("B"))))
    }

    @Test fun `shipments picked up are not delivered yet`() {
        factory.produce(listOf(
            Shipment(ShipmentId("1"), LocationId("A"))
        ))
        factory.pickUpNextShipment()
        assertThat(factory.hasAllShipmentsDelivered(), equalTo(false))
    }
}
