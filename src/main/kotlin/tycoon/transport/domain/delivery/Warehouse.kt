package tycoon.transport.domain.delivery

import tycoon.transport.domain.Location
import tycoon.transport.domain.Transport

class Warehouse(
    override val location: Location,
    private val deliveryListener: DeliveryListener
) : Station {
    override fun transportArrived(transport: Transport) {
        deliveryListener.cargoDelivered(transport.unload())
    }
}
