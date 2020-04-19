package tycoon.transport.domain

import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.DistanceMap
import tycoon.transport.domain.carrier.Leg
import tycoon.transport.domain.carrier.Router
import tycoon.transport.domain.delivery.Station

class TransportMap(private val factory: Station) : DistanceMap, StationMap, Router {
    private val stations = mutableMapOf(factory.location to factory)
    private val distances = mutableMapOf<Location, Distance>()

    fun addStation(
        station: Station,
        distance: Distance
    ) {
        stations[station.location] = station
        distances[station.location] = distance
    }

    override fun stationAt(location: Location): Station {
        return stations[location]
            ?: throw LocationUnknown()
    }

    override fun distanceTo(location: Location): Distance {
        return distances[location]
            ?: throw LocationUnknown()
    }

    override fun legBetween(origin: Location, destination: Location): Leg {
        if (origin != factory.location) {
            throw LegNotFound()
        }
        if (destination == factory.location) {
            throw LegNotFound()
        }
        return Leg(factory.location, destination, Distance(5))
    }
}
