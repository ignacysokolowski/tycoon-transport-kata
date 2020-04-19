package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

data class Journey private constructor(
    val destination: Location,
    private val distance: Distance
) {
    companion object {
        fun to(destination: Location, distance: Distance) = Journey(destination, distance)
    }

    fun advancedBy(distance: Distance) = copy(distance = this.distance - distance)
    fun atDestination() = distance == Distance(0)
}
