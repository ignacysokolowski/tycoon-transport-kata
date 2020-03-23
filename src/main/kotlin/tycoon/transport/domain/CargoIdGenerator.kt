package tycoon.transport.domain

class CargoIdGenerator {
    private var latest = 0

    fun next(): CargoId {
        latest += 1
        return CargoId(latest.toString())
    }
}
