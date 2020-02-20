package tycoon.transport.domain

class Factory {
    private var shipmentsWaiting = emptyList<Shipment>()

    fun shipmentsWaiting() = shipmentsWaiting

    fun collectShipments(shipments: List<Shipment>) {
        shipmentsWaiting = shipments
    }
}
