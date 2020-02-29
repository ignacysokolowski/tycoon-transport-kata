package tycoon.transport.domain

import java.util.ArrayDeque

class Factory : DeliveryListener {
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

    override fun shipmentDelivered(shipmentId: ShipmentId) {
        if (!shipmentsPickedUp.removeIf { it.id == shipmentId }) {
            throw ShipmentNotPickedUp()
        }
    }

    fun hasAllShipmentsDelivered() = shipmentsPickedUp.isEmpty() and shipmentsWaiting.isEmpty()
}
