package tycoon.transport.domain

class CargoIds {
    private var next = 0

    fun next(): CargoId {
        next += 1
        return CargoId(next.toString())
    }
}
