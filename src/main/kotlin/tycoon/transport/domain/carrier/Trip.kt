package tycoon.transport.domain.carrier

import tycoon.transport.domain.LocationId

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

    fun journeyDestination() = journey.destination

    fun backToOrigin(): Trip {
        if (headingBackToOrigin()) {
            return copy()
        }
        return copy(journey = Journey.to(origin, distance))
    }

    private fun headingBackToOrigin() = journey.destination == origin

    fun advancedBy(distance: Distance): Trip {
        if (this.distance == Distance(0)) {
            return copy()
        }
        return copy(journey = journey.advancedBy(distance))
    }

    fun journeyComplete() = journey.atDestination()
}
