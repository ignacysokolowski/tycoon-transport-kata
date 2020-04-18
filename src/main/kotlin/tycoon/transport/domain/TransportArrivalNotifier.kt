package tycoon.transport.domain

import tycoon.transport.domain.carrier.TransportListener

class TransportArrivalNotifier(private val map: LocationMap) : TransportListener {
    override fun transportArrived(transport: Transport, locationId: LocationId) {
        map.locationAt(locationId).transportArrived(transport)
    }
}
