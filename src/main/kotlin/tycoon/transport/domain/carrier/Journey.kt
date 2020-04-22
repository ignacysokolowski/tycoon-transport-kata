package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

data class Journey private constructor(
    val destination: Location,
    private val distance: Distance
) {
    companion object {
        fun to(destination: Location, distance: Distance) = Journey(destination, distance)
    }

    fun advanced() = copy(distance = this.distance - Distance(1))
    fun atDestination() = distance == Distance(0)
}
