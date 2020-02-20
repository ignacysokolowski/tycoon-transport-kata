package tycoon.transport.domain

import java.util.ArrayDeque

class Factory {
    private var shipmentsWaiting = ArrayDeque<Shipment>()

    fun collectShipments(shipments: List<Shipment>) {
        shipmentsWaiting = ArrayDeque(shipments)
    }

    fun pickUpNextShipment(): Shipment {
        return try {
            shipmentsWaiting.pop()
        } catch (e: NoSuchElementException) {
            throw AllShipmentsPickedUp()
        }
    }

    fun shipmentsWaiting() = shipmentsWaiting.toList()

    fun hasShipmentsWaiting() = shipmentsWaiting.isNotEmpty()
}
