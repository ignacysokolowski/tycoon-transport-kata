package tycoon.transport.domain

data class Trip(private val distance: Distance) {
    fun advancedBy(distance: Distance) = Trip(this.distance - distance)
    fun atDestination(): Boolean {
        return false
    }
}
