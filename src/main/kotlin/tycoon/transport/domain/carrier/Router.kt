package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

interface Router {
    fun inPlaceTripAtOrigin(): Trip
    fun tripTo(destination: Location): Trip
}
