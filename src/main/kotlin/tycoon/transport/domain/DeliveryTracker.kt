package tycoon.transport.domain

class DeliveryTracker : DeliveryListener {
    private val scheduled = mutableListOf<CargoId>()

    fun scheduleDeliveryOf(cargoId: CargoId) {
        scheduled.add(cargoId)
    }

    override fun cargoDelivered(cargoId: CargoId) {
        scheduled.remove(cargoId)
    }

    fun allCargoesDelivered() = scheduled.isEmpty()
}
