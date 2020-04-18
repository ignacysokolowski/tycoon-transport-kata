package tycoon.transport.domain.delivery

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Transport

interface Location {
    val locationId: LocationId
    fun transportArrived(transport: Transport)
}
