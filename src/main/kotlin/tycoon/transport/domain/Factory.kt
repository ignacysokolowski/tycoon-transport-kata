package tycoon.transport.domain

import java.util.ArrayDeque

class Factory {
    private var shipmentsWaiting = ArrayDeque<Shipment>()

    fun collectShipments(shipments: List<Shipment>) {
        shipmentsWaiting = ArrayDeque(shipments)
    }

    fun pickUpNextShipment(): Shipment {
        return shipmentsWaiting.pop()
            ?: throw RuntimeException()
    }

    fun shipmentsWaiting() = shipmentsWaiting.toList()
}
