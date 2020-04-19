package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

interface Router {
    fun legBetween(origin: Location, destination: Location): Leg
}
