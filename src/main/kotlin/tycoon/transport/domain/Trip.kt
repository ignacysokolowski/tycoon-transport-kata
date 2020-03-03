package tycoon.transport.domain

data class Trip private constructor(
    private val origin: LocationId,
    private val distance: Distance,
    private val journey: Journey
) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance) =
            Trip(origin, distance, Journey.to(destination, distance))

        fun inPlace(locationId: LocationId) = between(locationId, locationId, Distance(0))
    }

    fun journey() = journey

    fun destination() = journey.destination

    fun backToOrigin(): Trip {
        if (journey.destination == origin) {
            return copy()
        }
        return copy(journey = Journey.to(origin, distance))
    }

    fun advancedBy(distance: Distance): Trip {
        if (this.distance == Distance(0)) {
            return copy()
        }
        return copy(journey = journey.advancedBy(distance))
    }

    fun journeyComplete() = journey.atDestination()
}
