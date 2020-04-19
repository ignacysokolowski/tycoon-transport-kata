package tycoon.transport.domain.delivery

import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Transport

class Warehouse(
    override val locationId: LocationId,
    private val deliveryListener: DeliveryListener
) : Station {
    override fun transportArrived(transport: Transport) {
        deliveryListener.cargoDelivered(transport.unload())
    }
}
