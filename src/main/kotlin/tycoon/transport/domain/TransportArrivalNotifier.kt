package tycoon.transport.domain

import tycoon.transport.domain.carrier.TransportListener

class TransportArrivalNotifier(private val map: StationMap) : TransportListener {
    override fun transportArrived(transport: Transport, locationId: LocationId) {
        map.stationAt(locationId).transportArrived(transport)
    }
}
