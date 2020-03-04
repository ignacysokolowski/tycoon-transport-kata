package tycoon.transport.domain

interface TruckListener {
    fun truckArrived(truck: Transport, locationId: LocationId)
}
