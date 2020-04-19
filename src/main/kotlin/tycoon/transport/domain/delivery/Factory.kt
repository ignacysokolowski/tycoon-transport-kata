package tycoon.transport.domain.delivery

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport
import tycoon.transport.domain.cargo.Cargo

class Factory(private val deliveryScheduler: DeliveryScheduler) : Station {
    override val location = Location("FACTORY")
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