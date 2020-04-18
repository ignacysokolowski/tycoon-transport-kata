package tycoon.transport.domain

import tycoon.transport.domain.cargo.CargoId

interface DeliveryScheduler {
    fun scheduleDeliveryOf(cargoId: CargoId)
}
