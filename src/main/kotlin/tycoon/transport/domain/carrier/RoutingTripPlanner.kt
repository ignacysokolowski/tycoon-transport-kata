package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

class RoutingTripPlanner(
    private val origin: Location,
    private val router: Router
) {
    fun inPlaceTripAtOrigin() = Trip.inPlace(origin)

    fun tripTo(destination: Location): Trip {
        return Trip.from(router.legBetween(origin, destination))
    }
}
