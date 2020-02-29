package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Truck

class TruckSpy {
    private val arrivals = mutableListOf<Pair<Truck, LocationId>>()

    fun arrivalsOf(truck: Truck): List<LocationId> {
        return arrivals.filter { it.first == truck }.map { it.second }
    }

    fun truckArrived(truck: Truck, locationId: LocationId) {
        arrivals.add(Pair(truck, locationId))
    }
}
