package tycoon.transport.domain

class Trip private constructor(
    private val origin: LocationId,
    private val destination: LocationId,
    private val distance: Distance
) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance): Trip {
            return Trip(origin, destination, distance)
        }
    }

    fun journey(): Journey {
        return Journey.to(destination, distance)
    }

    fun reversed(): Trip {
        return Trip(destination, origin, distance)
    }
}
