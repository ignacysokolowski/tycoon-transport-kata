package tycoon.transport.domain

import tycoon.transport.domain.cargo.CargoId

interface DeliveryListener {
    fun cargoDelivered(cargoId: CargoId)
}
