package tycoon.transport.domain.delivery

import java.util.ArrayDeque
import tycoon.transport.domain.cargo.Cargo

class ContainerStack {
    private var waiting = ArrayDeque<Cargo>()

    fun put(cargoes: List<Cargo>) {
        waiting = ArrayDeque(cargoes)
    }

    fun put(cargo: Cargo) {
        waiting.add(cargo)
    }

    fun pickUpNext(): Cargo {
        return try {
            waiting.pop()
        } catch (e: NoSuchElementException) {
            throw AllCargoPickedUp()
        }
    }
}
