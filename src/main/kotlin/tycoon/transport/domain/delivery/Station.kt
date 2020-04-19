package tycoon.transport.domain.delivery

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Transport

interface Station {
    val locationId: LocationId
    fun transportArrived(transport: Transport)
}
