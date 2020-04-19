package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

interface TripPlanner {
    fun inPlaceTripAtOrigin(): Trip
    fun tripTo(destination: Location): Trip
}
