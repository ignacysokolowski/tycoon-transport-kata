package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

class MapRouter(private val origin: Location, private val map: DistanceMap) : Router {
    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    override fun tripTo(destination: Location) =
        Trip.between(origin, destination, map.distanceTo(destination))
}
