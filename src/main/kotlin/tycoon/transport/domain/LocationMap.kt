package tycoon.transport.domain

interface LocationMap {
    fun locationAt(locationId: LocationId): Location
}
