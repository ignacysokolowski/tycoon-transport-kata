package tycoon.transport.domain

class ShipmentIds {
    private val next = 1

    fun next(): String {
        return next.toString()
    }
}
