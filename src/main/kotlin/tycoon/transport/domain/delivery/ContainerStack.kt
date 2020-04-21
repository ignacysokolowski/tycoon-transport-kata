package tycoon.transport.domain.delivery

import java.util.ArrayDeque
import tycoon.transport.domain.cargo.Cargo

class ContainerStack {
    private val waiting = ArrayDeque<Cargo>()

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
