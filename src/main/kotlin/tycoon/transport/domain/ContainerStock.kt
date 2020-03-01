package tycoon.transport.domain

import java.util.ArrayDeque

class ContainerStock {
    private var waiting = ArrayDeque<Cargo>()
    private val pickedUp = mutableListOf<Cargo>()

    fun put(cargo: List<Cargo>) {
        waiting = ArrayDeque(cargo)
    }

    fun pickUpNext(): Cargo {
        val cargo = try {
            waiting.pop()
        } catch (e: NoSuchElementException) {
            throw AllCargoPickedUp()
        }
        pickedUp.add(cargo)
        return cargo
    }

    fun markDelivered(shipmentId: ShipmentId) {
        if (!pickedUp.removeIf { it.id == shipmentId }) {
            throw CargoNotPickedUp()
        }
    }

    fun allDelivered() = pickedUp.isEmpty() and waiting.isEmpty()
}
