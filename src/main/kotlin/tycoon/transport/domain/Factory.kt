package tycoon.transport.domain

class Factory : DeliveryListener {
    val locationId = LocationId("FACTORY")
    private val containerStock = ContainerStock()

    fun produce(cargo: List<Cargo>) {
        containerStock.put(cargo)
    }

    fun pickUpNextCargo() = containerStock.pickUpNext()

    override fun cargoDelivered(cargoId: CargoId) {
        containerStock.markDelivered(cargoId)
    }

    fun hasAllCargoDelivered() = containerStock.allDelivered()

    fun transportArrived(transport: Transport) {
        val cargo = pickUpNextCargo()
        transport.pickUp(cargo.id)
    }
}
