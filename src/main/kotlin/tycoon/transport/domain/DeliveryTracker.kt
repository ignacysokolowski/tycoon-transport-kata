package tycoon.transport.domain

class DeliveryTracker : DeliveryListener {
    private var allCargoDelivered = true

    fun scheduleDeliveryOf(cargoId: CargoId) {
        allCargoDelivered = false
    }

    override fun cargoDelivered(cargoId: CargoId) {
        allCargoDelivered = true
    }

    fun allCargoesDelivered() = allCargoDelivered
}
