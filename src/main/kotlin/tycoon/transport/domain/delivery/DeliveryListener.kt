package tycoon.transport.domain.delivery

import tycoon.transport.domain.cargo.CargoId

interface DeliveryListener {
    fun cargoDelivered(cargoId: CargoId)
}
