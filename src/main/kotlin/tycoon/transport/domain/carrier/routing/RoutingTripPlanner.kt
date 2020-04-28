package tycoon.transport.domain.carrier.routing

import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Trip
import tycoon.transport.domain.carrier.TripPlanner

class RoutingTripPlanner(
    private val origin: Location,
    private val router: Router
) : TripPlanner {
    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    override fun tripTo(destination: Location) =
        router.firstLegBetween(origin, destination).newTrip()
}
