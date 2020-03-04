package tycoon.transport.domain

class Factory : Location, DeliveryListener {
    override val locationId = LocationId("FACTORY")
    private val containerStock = ContainerStock()

    fun produce(cargoes: List<Cargo>) {
        containerStock.put(cargoes)
    }

    fun pickUpNextCargo() = containerStock.pickUpNext()

    override fun cargoDelivered(cargoId: CargoId) {
        containerStock.markDelivered(cargoId)
    }

    fun hasAllCargoesDelivered() = containerStock.allDelivered()

    override fun transportArrived(transport: Transport) {
        val cargo = try {
            pickUpNextCargo()
        } catch (e: AllCargoPickedUp) {
            return
        }
        transport.load(cargo)
    }
}
