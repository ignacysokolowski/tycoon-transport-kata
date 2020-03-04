package tycoon.transport.domain

class TransportMap {
    private val distances = mutableMapOf<LocationId, Distance>()

    fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }

    fun addDistanceTo(location: LocationId, distance: Distance) {
        distances[location] = distance
    }
}
