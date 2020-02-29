package tycoon.transport.domain

data class Trip(
    private val destination: LocationId,
    private val distance: Distance
) {
    fun advancedBy(distance: Distance) = Trip(destination, this.distance - distance)
    fun atDestination() = distance == Distance(0)
}
