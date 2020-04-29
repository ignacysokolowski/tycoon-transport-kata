package tycoon.transport.domain.carrier.routing

import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Trip

data class Leg(val origin: Location, val destination: Location, private val distance: Distance) {
    fun newTrip() = Trip.between(origin, destination, distance)
}
