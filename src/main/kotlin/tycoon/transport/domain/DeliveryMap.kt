package tycoon.transport.domain

class DeliveryMap {
    private val distances = mutableMapOf<String, Int>()

    fun distanceTo(warehouseId: String): Int {
        return distances[warehouseId]
            ?: throw WarehouseUnknown()
    }

    fun addWarehouseWithDistance(distance: Int, warehouseId: String) {
        distances[warehouseId] = distance
    }
}
