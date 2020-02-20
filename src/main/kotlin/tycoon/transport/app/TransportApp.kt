package tycoon.transport.app

class TransportApp {
    private val warehouses = mutableListOf<String>()
    private val totalDeliveryTime = 5

    fun ship(warehouseId: String) {
        if (!warehouses.contains(warehouseId)) {
            throw RuntimeException("Unknown destination")
        }
    }

    fun totalDeliveryTime(): Int {
        return totalDeliveryTime
    }

    fun addWarehouse(warehouseId: String) {
        this.warehouses.add(warehouseId)
    }
}
