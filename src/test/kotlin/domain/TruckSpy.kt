package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Truck

class TruckSpy {
    private val arrivals = mutableMapOf<Truck, MutableList<LocationId>>()

    fun arrivalsOf(truck: Truck): List<LocationId> {
        return arrivals[truck] ?: emptyList()
    }

    fun truckArrived(truck: Truck, locationId: LocationId) {
        if (!arrivals.containsKey(truck)) {
            arrivals[truck] = mutableListOf()
        }
        arrivals[truck]!!.add(locationId)
    }
}
