package tycoon.transport.domain

interface DeliveryListener {
    fun cargoDelivered(cargoId: CargoId)
}
