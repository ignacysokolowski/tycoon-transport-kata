package tycoon.transport.domain.delivery

import tycoon.transport.domain.cargo.CargoId

interface DeliveryScheduler {
    fun scheduleDeliveryOf(cargoId: CargoId)
}
