package tycoon.transport.domain

class TransportMap(private val factory: Factory) : DistanceMap {
    private val locations = mutableMapOf<LocationId, Location>()
    private val distances = mutableMapOf<LocationId, Distance>()

    override fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }

    fun addDistanceTo(location: LocationId, distance: Distance) {
        distances[location] = distance
    }

    fun addLocation(id: LocationId, location: Location) {
        locations[id] = location
    }

    fun locationAt(locationId: LocationId): Location {
        if (locationId == factory.locationId) {
            return factory
        }
        return locations[locationId]
            ?: throw LocationUnknown()
    }
}
