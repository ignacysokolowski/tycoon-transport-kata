package tycoon.transport.domain

import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.DistanceMap
import tycoon.transport.domain.delivery.Location

class TransportMap(factory: Location) : DistanceMap, LocationMap {
    private val locations = mutableMapOf(factory.locationId to factory)
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
