package tycoon.transport.domain

class Trip private constructor(
    private val destination: LocationId,
    private val distance: Distance
) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance): Trip {
            return Trip(destination, distance)
        }
    }

    fun journey(): Journey {
        return Journey.to(destination, distance)
    }
}
