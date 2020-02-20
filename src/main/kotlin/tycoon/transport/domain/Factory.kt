package tycoon.transport.domain

class Factory {
    private var shipmentsWaiting = emptyList<Shipment>()

    fun collectShipments(shipments: List<Shipment>) {
        shipmentsWaiting = shipments
    }

    fun shipmentsWaiting() = shipmentsWaiting
}
