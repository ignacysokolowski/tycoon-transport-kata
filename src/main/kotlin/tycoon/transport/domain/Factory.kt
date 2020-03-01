package tycoon.transport.domain

class Factory(distanceMap: DistanceMap) : DeliveryListener {
    val locationId = LocationId("FACTORY")
    private val router = Router(locationId, distanceMap)
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
        val cargo = try {
            pickUpNextCargo()
        } catch (e: AllCargoPickedUp) {
            return
        }
        transport.pickUp(cargo.id)
        transport.startTrip(router.tripTo(cargo.destination))
    }
}
