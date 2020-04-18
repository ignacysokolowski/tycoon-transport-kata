package tycoon.transport.domain.carrier

import tycoon.transport.domain.LocationId

class MapRouter(private val origin: LocationId, private val map: DistanceMap) : Router {
    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    override fun tripTo(destination: LocationId) =
        Trip.between(origin, destination, map.distanceTo(destination))
}
