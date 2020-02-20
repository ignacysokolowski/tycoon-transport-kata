package tycoon.transport.app

class TransportApp(private val warehouses: Map<String, Int>) {
    private var totalDeliveryTime = 0

    fun ship(warehouseId: String) {
        totalDeliveryTime = warehouses[warehouseId]
            ?: throw RuntimeException("Unknown destination")
    }

    fun totalDeliveryTime() = totalDeliveryTime
}
