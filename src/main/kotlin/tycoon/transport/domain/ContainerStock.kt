package tycoon.transport.domain

import java.util.ArrayDeque

class ContainerStock {
    private var waiting = ArrayDeque<Cargo>()

    fun put(cargoes: List<Cargo>) {
        waiting = ArrayDeque(cargoes)
    }

    fun pickUpNext(): Cargo {
        val cargo = try {
            waiting.pop()
        } catch (e: NoSuchElementException) {
            throw AllCargoPickedUp()
        }
        return cargo
    }
}
