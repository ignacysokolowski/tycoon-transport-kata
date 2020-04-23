package tycoon.transport.domain.delivery

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport
import tycoon.transport.domain.cargo.Cargo

class Factory(private val deliveryScheduler: DeliveryScheduler) : Station {
    override val location = Location("FACTORY")
    private val containerStack = ContainerStack()
    private val terminal = Terminal()

    fun produce(cargoes: List<Cargo>) {
        cargoes.forEach {
            handleProduced(it)
            deliveryScheduler.scheduleDeliveryOf(it.id)
        }
    }

    private fun handleProduced(cargo: Cargo) {
        val transport = try {
            terminal.nextTransport()
        } catch (e: TerminalEmpty) {
            containerStack.put(cargo)
            return
        }
        transport.load(cargo)
    }

    override fun transportArrived(transport: Transport) {
        val cargo = try {
            containerStack.pickUpNext()
        } catch (e: AllCargoPickedUp) {
            terminal.enqueue(transport)
            return
        }
        transport.load(cargo)
    }
}
