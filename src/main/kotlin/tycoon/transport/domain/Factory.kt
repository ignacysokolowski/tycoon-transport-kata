package tycoon.transport.domain

import java.util.ArrayDeque

class Factory {
    private var shipmentsWaiting = ArrayDeque<Shipment>()
    private val shipmentsPickedUp = mutableListOf<Shipment>()

    fun collectShipments(shipments: List<Shipment>) {
        shipmentsWaiting = ArrayDeque(shipments)
    }

    fun pickUpNextShipment(): Shipment {
        val shipment = try {
            shipmentsWaiting.pop()
        } catch (e: NoSuchElementException) {
            throw AllShipmentsPickedUp()
        }
        shipmentsPickedUp.add(shipment)
        return shipment
    }

    fun shipmentDelivered(shipment: Shipment) {
        if (!shipmentsPickedUp.remove(shipment)) {
            throw ShipmentNotPickedUp()
        }
    }

    fun hasShipmentsWaiting() = shipmentsWaiting.isNotEmpty()

    fun hasAllShipmentsDelivered(): Boolean {
        return (shipmentsPickedUp.isEmpty() and !hasShipmentsWaiting())
    }
}
