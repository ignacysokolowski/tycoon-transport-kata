package tycoon.transport.domain.carrier

import tycoon.transport.domain.LocationId

interface Router {
    fun inPlaceTripAtOrigin(): Trip
    fun tripTo(destination: LocationId): Trip
}
