package tycoon.transport.domain

interface TruckListener {
    fun truckArrived(truck: Truck, locationId: LocationId)
}
