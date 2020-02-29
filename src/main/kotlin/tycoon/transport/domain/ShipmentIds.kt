package tycoon.transport.domain

class ShipmentIds {
    private var next = 0

    fun next(): ShipmentId {
        next += 1
        return ShipmentId(next.toString())
    }
}
