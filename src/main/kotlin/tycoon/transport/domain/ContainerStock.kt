package tycoon.transport.domain

import java.util.ArrayDeque

class ContainerStock {
    private var waiting = ArrayDeque<Cargo>()
    private val pickedUp = mutableListOf<Cargo>()

    fun put(cargoes: List<Cargo>) {
        waiting = ArrayDeque(cargoes)
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

    fun markDelivered(cargoId: CargoId) {
        if (!pickedUp.removeIf { it.id == cargoId }) {
            throw CargoNotPickedUp()
        }
    }

    fun allDelivered() = pickedUp.isEmpty() and waiting.isEmpty()
}
