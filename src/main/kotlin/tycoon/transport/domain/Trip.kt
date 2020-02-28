package tycoon.transport.domain

class Trip(val distance: Distance) {
    fun advancedBy(distance: Distance) = Trip(this.distance - distance)
}
