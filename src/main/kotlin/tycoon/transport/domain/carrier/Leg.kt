package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

data class Leg(val origin: Location, val destination: Location, val distance: Distance) {
    fun newTrip() = Trip.between(origin, destination, distance)
}
