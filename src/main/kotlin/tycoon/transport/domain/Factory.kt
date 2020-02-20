package tycoon.transport.domain

class Factory {
    private var shipmentsWaiting = emptyList<Shipment>()

    fun shipmentsWaiting(): List<Shipment> {
        return shipmentsWaiting
    }

    fun collectShipments(shipments: List<Shipment>) {
        shipmentsWaiting = shipments
    }
}
