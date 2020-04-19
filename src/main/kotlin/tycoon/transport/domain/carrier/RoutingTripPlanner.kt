package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

class RoutingTripPlanner(
    private val origin: Location,
    private val router: Router
) : TripPlanner {
    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    override fun tripTo(destination: Location) =
        Trip.from(router.legBetween(origin, destination))
}
