package tycoon.transport.domain

data class Trip private constructor(
    private val origin: LocationId,
    private val destination: LocationId,
    private val distance: Distance,
    private val progress: Distance
) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance): Trip {
            return Trip(origin, destination, distance, distance)
        }
    }

    fun journey(): Journey {
        return Journey.to(destination, progress)
    }

    fun reversed(): Trip {
        return copy(origin = destination, destination = origin, progress = distance)
    }

    fun advancedBy(distance: Distance): Trip {
        return copy(progress = progress - distance)
    }
}
