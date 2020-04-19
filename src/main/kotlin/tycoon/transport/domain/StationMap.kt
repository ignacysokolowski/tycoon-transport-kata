package tycoon.transport.domain

import tycoon.transport.domain.delivery.Station

interface StationMap {
    fun stationAt(locationId: LocationId): Station
}
