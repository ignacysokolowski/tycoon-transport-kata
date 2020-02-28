package tycoon.transport.domain

data class Trip(val distance: Distance) {
    fun advancedBy(distance: Distance) = Trip(this.distance - distance)
}
