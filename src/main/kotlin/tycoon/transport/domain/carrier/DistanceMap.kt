package tycoon.transport.domain.carrier

import tycoon.transport.domain.LocationId

interface DistanceMap {
    fun distanceTo(location: LocationId): Distance
}
