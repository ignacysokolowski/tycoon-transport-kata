package tycoon.transport.domain

class Router(private val origin: LocationId, private val map: DistanceMap) {
    fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    fun tripTo(destination: LocationId) =
        Trip.between(origin, destination, map.distanceTo(destination))
}
