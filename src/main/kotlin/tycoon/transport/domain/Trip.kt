package tycoon.transport.domain

data class Trip private constructor(
    private val origin: LocationId,
    private val distance: Distance,
    private val journey: Journey
) {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance) =
            Trip(origin, distance, Journey.to(destination, distance))
    }

    fun journey() = journey

    fun reversed() = copy(origin = journey.destination, journey = Journey.to(origin, distance))

    fun advancedBy(distance: Distance) = copy(journey = journey.advancedBy(distance))
}
