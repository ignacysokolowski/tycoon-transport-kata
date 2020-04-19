package tycoon.transport.domain

import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Leg
import tycoon.transport.domain.carrier.Router
import tycoon.transport.domain.delivery.Station

class TransportMap(private val factory: Station) : StationMap, Router {
    private val stations = mutableMapOf(factory.location to factory)
    private val legs = mutableListOf<Leg>()

    fun addStation(
        station: Station,
        distance: Distance
    ) {
        stations[station.location] = station
        legs.add(Leg(factory.location, station.location, distance))
    }

    override fun stationAt(location: Location): Station {
        return stations[location]
            ?: throw LocationUnknown()
    }

    override fun legBetween(origin: Location, destination: Location): Leg {
        return legs.firstOrNull { it.origin == origin && it.destination == destination }
            ?: throw LegNotFound()
    }
}
