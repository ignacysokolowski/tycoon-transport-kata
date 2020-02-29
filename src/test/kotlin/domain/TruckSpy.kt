package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Truck
import tycoon.transport.domain.TruckListener

data class TruckArrival(val truck: Truck, val destination: LocationId)

class TruckSpy : TruckListener {
    val arrivals = mutableListOf<TruckArrival>()

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        arrivals.add(TruckArrival(truck, locationId))
    }
}
