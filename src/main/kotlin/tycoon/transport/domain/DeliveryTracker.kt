package tycoon.transport.domain

class DeliveryTracker {
    private var allCargoDelivered = true

    fun scheduleDeliveryOf(cargoId: CargoId) {
        allCargoDelivered = false
    }

    fun allCargoesDelivered() = allCargoDelivered
}
