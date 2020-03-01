package tycoon.transport.domain

class Factory : DeliveryListener {
    val locationId = LocationId("FACTORY")
    private val containerStock = ContainerStock()

    fun produce(shipments: List<Shipment>) {
        containerStock.put(shipments)
    }

    fun pickUpNextShipment() = containerStock.pickUpNext()

    override fun shipmentDelivered(shipmentId: ShipmentId) {
        containerStock.markDelivered(shipmentId)
    }

    fun hasAllShipmentsDelivered() = containerStock.allDelivered()
}
