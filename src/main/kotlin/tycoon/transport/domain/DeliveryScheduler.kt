package tycoon.transport.domain

interface DeliveryScheduler {
    fun scheduleDeliveryOf(cargoId: CargoId)
}
