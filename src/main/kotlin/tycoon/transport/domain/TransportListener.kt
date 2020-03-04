package tycoon.transport.domain

interface TransportListener {
    fun transportArrived(transport: Transport, locationId: LocationId)
}
