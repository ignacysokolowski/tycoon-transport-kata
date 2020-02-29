package tycoon.transport.domain

data class Journey private constructor(
    val destination: LocationId,
    private val distance: Distance
) {
    companion object {
        fun to(destination: LocationId, distance: Distance) = Journey(destination, distance)
    }

    fun advancedBy(distance: Distance) = copy(distance = this.distance - distance)
    fun atDestination() = distance == Distance(0)
}
