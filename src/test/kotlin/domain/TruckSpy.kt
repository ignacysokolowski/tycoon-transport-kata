package domain

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Transport
import tycoon.transport.domain.Truck
import tycoon.transport.domain.TruckListener

data class TransportArrival(val transport: Transport, val destination: LocationId)

class TruckSpy : TruckListener {
    val arrivals = mutableListOf<TransportArrival>()

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        arrivals.add(TransportArrival(truck, locationId))
    }
}
