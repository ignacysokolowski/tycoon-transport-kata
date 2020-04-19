package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

class RoutingTripPlanner {
    fun inPlaceTripAtOrigin() = Trip.inPlace(Location("ORIGIN"))
}
