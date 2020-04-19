package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location

interface DistanceMap {
    fun distanceTo(location: Location): Distance
}
