package tycoon.transport.domain

import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Leg
import tycoon.transport.domain.carrier.Router
import tycoon.transport.domain.delivery.Station

class TransportMap(private val factory: Station) : StationMap, Router {
    private val stations = mutableMapOf(factory.location to factory)
    private val legs = mutableListOf<Leg>()

    fun addStation(station: Station, distance: Distance) {
        addStationBehind(factory.location, station, distance)
    }

    fun addStationBehind(location: Location, station: Station, distance: Distance) {
        if (!stations.containsKey(location)) {
            throw LocationUnknown()
        }
        if (stationAt(location) != factory && firstLegBetween(factory.location, location) == null) {
            throw IllegalArgumentException(
                "Can not add stations behind $location as " +
                    "it's not directly connected to the factory"
            )
        }
        stations[station.location] = station
        legs.add(Leg(location, station.location, distance))
    }

    override fun stationAt(location: Location): Station {
        return stations[location]
            ?: throw LocationUnknown()
    }

    override fun legBetween(origin: Location, destination: Location): Leg {
        return firstLegBetween(origin, destination)
            ?: throw LegNotFound()
    }

    private fun firstLegBetween(origin: Location, destination: Location) =
        legs.firstOrNull { it.origin == origin && it.destination == destination }
}
