package tycoon.transport.domain

class Factory : DeliveryListener {
    val locationId = LocationId("FACTORY")
    private val containerStock = ContainerStock()

    fun produce(cargo: List<Cargo>) {
        containerStock.put(cargo)
    }

    fun pickUpNextCargo() = containerStock.pickUpNext()

    override fun cargoDelivered(shipmentId: ShipmentId) {
        containerStock.markDelivered(shipmentId)
    }

    fun hasAllCargoDelivered() = containerStock.allDelivered()
}
