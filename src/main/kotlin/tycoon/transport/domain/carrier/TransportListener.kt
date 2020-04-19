package tycoon.transport.domain.carrier

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport

interface TransportListener {
    fun transportArrived(transport: Transport, location: Location)
}
