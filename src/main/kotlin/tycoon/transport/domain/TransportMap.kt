package tycoon.transport.domain

import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.DistanceMap
import tycoon.transport.domain.delivery.Station

class TransportMap(factory: Station) : DistanceMap, StationMap {
    private val stations = mutableMapOf(factory.locationId to factory)
    private val distances = mutableMapOf<LocationId, Distance>()

    fun addStation(
        station: Station,
        distance: Distance
    ) {
        stations[station.locationId] = station
        distances[station.locationId] = distance
    }

    override fun stationAt(locationId: LocationId): Station {
        return stations[locationId]
            ?: throw LocationUnknown()
    }

    override fun distanceTo(location: LocationId): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }
}
