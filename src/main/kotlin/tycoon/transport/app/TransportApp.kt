package tycoon.transport.app

class TransportApp {
    private val warehouses = mutableMapOf<String, Int>()
    private var totalDeliveryTime = 0

    fun ship(warehouseId: String) {
        if (!warehouses.contains(warehouseId)) {
            throw RuntimeException("Unknown destination")
        }
        totalDeliveryTime = 5
    }

    fun totalDeliveryTime(): Int {
        return totalDeliveryTime
    }

    fun addWarehouse(warehouseId: String) {
        this.warehouses[warehouseId] = 5
    }
}
