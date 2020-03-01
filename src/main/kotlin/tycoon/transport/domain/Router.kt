package tycoon.transport.domain

class Router(private val origin: LocationId, private val map: DistanceMap) {
    fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    fun tripTo(locationId: LocationId): Trip {
        return Trip.between(origin, locationId, map.distanceTo(locationId))
    }
}
