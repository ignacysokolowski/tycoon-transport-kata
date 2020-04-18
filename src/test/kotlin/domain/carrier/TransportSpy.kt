package domain.carrier

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Transport
import tycoon.transport.domain.carrier.TransportListener

data class TransportArrival(val transport: Transport, val destination: LocationId)

class TransportSpy : TransportListener {
    val arrivals = mutableListOf<TransportArrival>()

    override fun transportArrived(transport: Transport, locationId: LocationId) {
        arrivals.add(TransportArrival(transport, locationId))
    }
}
