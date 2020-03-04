package tycoon.transport.domain

class TransportMap(factory: Factory) : DistanceMap, LocationMap {
    private val locations = mutableMapOf<LocationId, Location>(factory.locationId to factory)
    private val distances = mutableMapOf<LocationId, Distance>()

    fun addLocation(
        location: Location,
        distance: Distance
    ) {
        locations[location.locationId] = location
        distances[location.locationId] = distance
    }

    override fun locationAt(locationId: LocationId): Location {
        return locations[locationId]
            ?: throw LocationUnknown()
    }

    override fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }
}
