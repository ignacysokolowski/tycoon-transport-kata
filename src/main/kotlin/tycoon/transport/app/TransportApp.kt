package tycoon.transport.app

class TransportApp {
    private val warehouses = mutableListOf<String>()

    fun ship(warehouseId: String) {
        if (!warehouses.contains(warehouseId)) {
            throw RuntimeException("Unknown destination")
        }
    }

    fun totalDeliveryTime(): Int {
        return 5
    }

    fun addWarehouse(warehouseId: String) {
        this.warehouses.add(warehouseId)
    }
}
