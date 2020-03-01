package tycoon.transport.domain

class Router(private val origin: LocationId) {
    fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    fun tripTo(locationId: LocationId): Trip {
        return Trip.between(origin, locationId, Distance(3))
    }
}
