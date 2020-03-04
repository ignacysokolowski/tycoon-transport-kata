package tycoon.transport.domain

interface TruckListener {
    fun transportArrived(transport: Transport, locationId: LocationId)
}
