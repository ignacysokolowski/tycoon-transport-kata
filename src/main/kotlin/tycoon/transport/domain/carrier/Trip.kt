package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

data class Trip private constructor(
    private val origin: Location,
    private val distance: Distance,
    private val journey: Journey
) {
    companion object {
        fun between(origin: Location, destination: Location, distance: Distance) =
            Trip(origin, distance, Journey.to(destination, distance))

        fun inPlace(location: Location) = between(location, location, Distance(0))
        fun from(leg: Leg) = between(leg.origin, leg.destination, leg.distance)
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

    fun advanced(): Trip {
        if (this.distance == Distance(0)) {
            return copy()
        }
        return copy(journey = journey.advancedBy(Distance(1)))
    }

    fun journeyComplete() = journey.atDestination()
}
