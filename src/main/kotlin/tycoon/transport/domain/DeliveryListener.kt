package tycoon.transport.domain

interface DeliveryListener {
    fun shipmentDelivered(shipmentId: ShipmentId)
}
