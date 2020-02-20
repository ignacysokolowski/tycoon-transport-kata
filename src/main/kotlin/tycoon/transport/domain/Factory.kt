package tycoon.transport.domain

class Factory {
    private var shipmentsWaiting = emptyList<Shipment>()

    fun shipmentsWaiting(): List<Shipment> {
        return shipmentsWaiting
    }

    fun collectShipment(shipment: Shipment) {
        shipmentsWaiting = listOf(shipment)
    }
}
