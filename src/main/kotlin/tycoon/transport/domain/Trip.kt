package tycoon.transport.domain

data class Trip(private val distance: Distance, val destination: LocationId) {
    fun advancedBy(distance: Distance) = Trip(this.distance - distance, destination)
    fun atDestination() = distance == Distance(0)
}
