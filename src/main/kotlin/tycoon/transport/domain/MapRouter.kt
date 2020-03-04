package tycoon.transport.domain

class MapRouter(private val origin: LocationId, private val map: TransportMap) : Router {
    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    override fun tripTo(destination: LocationId) =
        Trip.between(origin, destination, map.distanceTo(destination))
}
