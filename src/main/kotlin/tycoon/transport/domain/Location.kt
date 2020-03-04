package tycoon.transport.domain

interface Location {
    val locationId: LocationId
    fun transportArrived(transport: Transport)
}
