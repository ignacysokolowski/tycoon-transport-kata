package tycoon.transport.domain.carrier.routing

import tycoon.transport.domain.Location

interface Router {
    fun firstLegBetween(origin: Location, destination: Location): Leg
}
