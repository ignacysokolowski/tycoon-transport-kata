package tycoon.transport.domain.delivery

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport
import tycoon.transport.domain.cargo.Cargo

class Factory(private val deliveryScheduler: DeliveryScheduler) : Station {
    override val location = Location("FACTORY")
    private val containerStack = ContainerStack()

    fun produce(cargoes: List<Cargo>) {
        cargoes.forEach {
            handleProduced(it)
            deliveryScheduler.scheduleDeliveryOf(it.id)
        }
    }

    private fun handleProduced(cargo: Cargo) {
        containerStack.put(cargo)
    }

    override fun transportArrived(transport: Transport) {
        val cargo = try {
            containerStack.pickUpNext()
        } catch (e: AllCargoPickedUp) {
            return
        }
        transport.load(cargo)
    }
}
