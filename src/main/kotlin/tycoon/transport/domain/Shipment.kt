package tycoon.transport.domain

data class Shipment(val destination: WarehouseId, val shipmentId: ShipmentId? = null)
