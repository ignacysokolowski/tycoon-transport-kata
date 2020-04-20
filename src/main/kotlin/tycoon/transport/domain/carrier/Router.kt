package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

interface Router {
    fun firstLegBetween(origin: Location, destination: Location): Leg
}
