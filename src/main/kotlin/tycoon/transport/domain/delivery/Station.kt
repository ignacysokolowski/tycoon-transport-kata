package tycoon.transport.domain.delivery

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport

interface Station {
    val location: Location
    fun transportArrived(transport: Transport)
}
