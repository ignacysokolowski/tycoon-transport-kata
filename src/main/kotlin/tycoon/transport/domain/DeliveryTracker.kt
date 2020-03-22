package tycoon.transport.domain

class DeliveryTracker : DeliveryListener {
    private val scheduled = mutableListOf<CargoId>()

    fun scheduleDeliveryOf(cargoId: CargoId) {
        scheduled.add(cargoId)
    }

    override fun cargoDelivered(cargoId: CargoId) {
        scheduled.clear()
    }

    fun allCargoesDelivered() = scheduled.isEmpty()
}
