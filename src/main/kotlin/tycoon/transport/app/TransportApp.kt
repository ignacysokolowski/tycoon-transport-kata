package tycoon.transport.app

class TransportApp {
    private val warehouses = mutableMapOf<String, Int>()
    private var totalDeliveryTime = 0

    fun ship(warehouseId: String) {
        totalDeliveryTime = warehouses[warehouseId]
            ?: throw RuntimeException("Unknown destination")
    }

    fun totalDeliveryTime(): Int {
        return totalDeliveryTime
    }

    fun addWarehouse(warehouseId: String) {
        this.warehouses[warehouseId] = 5
    }
}
