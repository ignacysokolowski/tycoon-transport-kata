package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Truck

data class TruckArrival(val truck: Truck, val destination: LocationId)

class TruckSpy {
    private val arrivals = mutableListOf<TruckArrival>()

    fun arrivalsOf(truck: Truck): List<LocationId> {
        return arrivals.filter { it.truck == truck }.map { it.destination }
    }

    fun truckArrived(truck: Truck, locationId: LocationId) {
        arrivals.add(TruckArrival(truck, locationId))
    }
}
