package domain.carrier

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport
import tycoon.transport.domain.carrier.TransportListener

data class TransportArrival(val transport: Transport, val destination: Location)

class TransportSpy : TransportListener {
    val arrivals = mutableListOf<TransportArrival>()

    override fun transportArrived(transport: Transport, location: Location) {
        arrivals.add(TransportArrival(transport, location))
    }
}
