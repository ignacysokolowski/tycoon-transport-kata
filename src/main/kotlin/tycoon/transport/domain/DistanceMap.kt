package tycoon.transport.domain

interface DistanceMap {
    fun distanceTo(location: LocationId): Distance
}
