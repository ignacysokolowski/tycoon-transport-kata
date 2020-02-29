package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Truck

class TruckSpy {
    private val arrivals = mutableListOf<LocationId>()

    fun arrivalsOf(truck: Truck): List<LocationId> {
        return arrivals
    }

    fun truckArrived(truck: Truck, locationId: LocationId) {
        arrivals.add(locationId)
    }
}
