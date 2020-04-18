package tycoon.transport.domain.cargo

class CargoIdGenerator {
    private var latest = 0

    fun next(): CargoId {
        latest += 1
        return CargoId(latest.toString())
    }
}
