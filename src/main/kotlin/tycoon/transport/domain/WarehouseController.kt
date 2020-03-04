package tycoon.transport.domain

class WarehouseController(private val deliveryListener: DeliveryListener) : Location {
    override fun transportArrived(transport: Transport) {
        deliveryListener.cargoDelivered(transport.unload())
    }
}
