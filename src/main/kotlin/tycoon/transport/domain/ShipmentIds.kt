package tycoon.transport.domain

class ShipmentIds {
    private var next = 0

    fun next(): String {
        next += 1
        return next.toString()
    }
}
