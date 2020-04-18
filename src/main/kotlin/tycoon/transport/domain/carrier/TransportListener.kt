package tycoon.transport.domain.carrier

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Transport

interface TransportListener {
    fun transportArrived(transport: Transport, locationId: LocationId)
}
