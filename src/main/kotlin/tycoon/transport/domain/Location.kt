package tycoon.transport.domain

interface Location {
    fun transportArrived(transport: Transport)
}
