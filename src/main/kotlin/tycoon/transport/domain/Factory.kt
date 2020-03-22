package tycoon.transport.domain

class Factory(private val deliveryScheduler: DeliveryScheduler) : Location {
    override val locationId = LocationId("FACTORY")
    private val containerStock = ContainerStock()

    fun produce(cargoes: List<Cargo>) {
        containerStock.put(cargoes)
        cargoes.forEach { deliveryScheduler.scheduleDeliveryOf(it.id) }
    }

    override fun transportArrived(transport: Transport) {
        val cargo = try {
            containerStock.pickUpNext()
        } catch (e: AllCargoPickedUp) {
            return
        }
        transport.load(cargo)
    }
}
