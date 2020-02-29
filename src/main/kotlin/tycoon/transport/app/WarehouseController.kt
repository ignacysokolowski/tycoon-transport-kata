package tycoon.transport.app

import tycoon.transport.domain.DeliveryListener
import tycoon.transport.domain.Transport

class WarehouseController(private val deliveryListener: DeliveryListener) {
    fun transportArrived(transport: Transport) {
        deliveryListener.shipmentDelivered(transport.dropOff())
    }
}
