package tycoon.transport.domain

class Factory(distanceMap: DistanceMap) : DeliveryListener {
    val locationId = LocationId("FACTORY")
    private val router = MapRouter(locationId, distanceMap)
    private val containerStock = ContainerStock()

    fun produce(cargoes: List<Cargo>) {
        containerStock.put(cargoes)
    }

    fun pickUpNextCargo() = containerStock.pickUpNext()

    override fun cargoDelivered(cargoId: CargoId) {
        containerStock.markDelivered(cargoId)
    }

    fun hasAllCargoesDelivered() = containerStock.allDelivered()

    fun transportArrived(transport: Transport) {
        val cargo = try {
            pickUpNextCargo()
        } catch (e: AllCargoPickedUp) {
            return
        }
        transport.load(cargo)
        transport.startTrip(router.tripTo(cargo.destination))
    }
}
