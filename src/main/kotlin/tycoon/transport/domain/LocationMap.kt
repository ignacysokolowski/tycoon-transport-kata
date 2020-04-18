package tycoon.transport.domain

import tycoon.transport.domain.delivery.Location

interface LocationMap {
    fun locationAt(locationId: LocationId): Location
}
