package tycoon.transport.domain

class TransportMap(private val factory: Factory) : DistanceMap {
    private val distances = mutableMapOf<LocationId, Distance>()

    override fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }

    fun addDistanceTo(location: LocationId, distance: Distance) {
        distances[location] = distance
    }

    fun locationAt(locationId: LocationId) = factory
}
