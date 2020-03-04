package tycoon.transport.domain

class Warehouse(
    override val locationId: LocationId,
    private val deliveryListener: DeliveryListener
) : Location {
    override fun transportArrived(transport: Transport) {
        deliveryListener.cargoDelivered(transport.unload())
    }
}
