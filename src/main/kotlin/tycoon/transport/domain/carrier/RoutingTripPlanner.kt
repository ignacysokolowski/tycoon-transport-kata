package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

class RoutingTripPlanner(private val origin: Location) {
    fun inPlaceTripAtOrigin() = Trip.inPlace(origin)
}
