package tycoon.transport.domain

class Trip(private val destination: LocationId) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance): Trip {
            return Trip(destination)
        }
    }

    fun journey(): Journey {
        return Journey.to(destination, Distance(3))
    }
}
