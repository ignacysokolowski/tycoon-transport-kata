package tycoon.transport.domain

import java.util.ArrayDeque

class ContainerStock {
    private var waiting = ArrayDeque<Shipment>()
    private val pickedUp = mutableListOf<Shipment>()

    fun put(shipments: List<Shipment>) {
        waiting = ArrayDeque(shipments)
    }

    fun pickUpNext(): Shipment {
        val shipment = try {
            waiting.pop()
        } catch (e: NoSuchElementException) {
            throw AllShipmentsPickedUp()
        }
        pickedUp.add(shipment)
        return shipment
    }

    fun markDelivered(shipmentId: ShipmentId) {
        if (!pickedUp.removeIf { it.id == shipmentId }) {
            throw ShipmentNotPickedUp()
        }
    }

    fun allDelivered() = pickedUp.isEmpty() and waiting.isEmpty()
}
