package tycoon.transport.domain

interface TruckListener {
    fun transportArrived(truck: Transport, locationId: LocationId)
}
