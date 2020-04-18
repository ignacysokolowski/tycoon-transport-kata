package tycoon.transport.domain

import tycoon.transport.domain.cargo.CargoId

class DeliveryTracker : DeliveryScheduler, DeliveryListener {
    private val scheduled = mutableListOf<CargoId>()

    override fun scheduleDeliveryOf(cargoId: CargoId) {
        scheduled.add(cargoId)
    }

    override fun cargoDelivered(cargoId: CargoId) {
        scheduled.remove(cargoId)
    }

    fun allCargoesDelivered() = scheduled.isEmpty()
}
