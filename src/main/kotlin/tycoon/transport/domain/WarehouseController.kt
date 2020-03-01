package tycoon.transport.domain

class WarehouseController(private val deliveryListener: DeliveryListener) {
    fun transportArrived(transport: Transport) {
        deliveryListener.cargoDelivered(transport.dropOff())
        transport.goBack()
    }
}
