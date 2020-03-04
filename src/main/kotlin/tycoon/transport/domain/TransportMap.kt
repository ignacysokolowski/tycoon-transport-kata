package tycoon.transport.domain

class TransportMap(private val factory: Factory) : DistanceMap {
    private val locations = mutableMapOf<LocationId, Location>(factory.locationId to factory)
    private val distances = mutableMapOf<LocationId, Distance>()

    fun addLocation(
        id: LocationId,
        location: Location,
        distance: Distance
    ) {
        locations[id] = location
        distances[id] = distance
    }

    fun locationAt(locationId: LocationId): Location {
        return locations[locationId]
            ?: throw LocationUnknown()
    }

    override fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }
}
