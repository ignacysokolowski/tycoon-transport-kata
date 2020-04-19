package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

class MapBasedTripPlanner(private val origin: Location, private val map: DistanceMap) : TripPlanner {
    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    override fun tripTo(destination: Location) =
        Trip.between(origin, destination, map.distanceTo(destination))
}
