package tycoon.transport.domain

interface DeliveryListener {
    fun cargoDelivered(shipmentId: ShipmentId)
}
