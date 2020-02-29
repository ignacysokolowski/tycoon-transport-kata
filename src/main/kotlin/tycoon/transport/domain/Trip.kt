package tycoon.transport.domain

data class Trip private constructor(
    private val origin: LocationId,
    private val destination: LocationId,
    private val distance: Distance,
    private val progress: Distance,
    private val journey: Journey
) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance): Trip {
            return Trip(origin, destination, distance, distance, Journey.to(destination, distance))
        }
    }

    fun journey(): Journey {
        return journey
    }

    fun reversed(): Trip {
        return copy(origin = destination, destination = origin, progress = distance, journey = Journey.to(origin, distance))
    }

    fun advancedBy(distance: Distance): Trip {
        return copy(journey = journey.advancedBy(distance))
    }
}
