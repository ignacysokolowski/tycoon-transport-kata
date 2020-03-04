package tycoon.transport.domain

class TransportMap(private val factory: Factory) : DistanceMap {
    private val locations = mutableMapOf<LocationId, Location>(factory.locationId to factory)
    private val distances = mutableMapOf<LocationId, Distance>()

    override fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }

    fun addDistanceTo(location: LocationId, distance: Distance) {
        distances[location] = distance
    }

    fun addLocation(
        id: LocationId,
        location: Location,
        distance: Distance
    ) {
        locations[id] = location
        addDistanceTo(id, distance)
    }

    fun locationAt(locationId: LocationId): Location {
        return locations[locationId]
            ?: throw LocationUnknown()
    }
}
