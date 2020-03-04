package tycoon.transport.domain

class TransportArrivalNotifier(private val map: LocationMap) : TransportListener {
    override fun transportArrived(transport: Transport, locationId: LocationId) {
        map.locationAt(locationId).transportArrived(transport)
    }
}
