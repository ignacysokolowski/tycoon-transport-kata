package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Truck

data class TruckArrival(val truck: Truck, val destination: LocationId)

class TruckSpy {
    val arrivals = mutableListOf<TruckArrival>()

    fun truckArrived(truck: Truck, locationId: LocationId) {
        arrivals.add(TruckArrival(truck, locationId))
    }
}
