package tycoon.transport.domain

class DistanceMap {
    private val distances = mutableMapOf<WarehouseId, Int>()

    fun distanceTo(warehouseId: WarehouseId): Int {
        return distances[warehouseId]
            ?: throw WarehouseUnknown()
    }

    fun addWarehouseWithDistance(distance: Int, warehouseId: WarehouseId) {
        distances[warehouseId] = distance
    }

    fun addWarehouse(distance: Distance, warehouseId: WarehouseId) {
        addWarehouseWithDistance(distance.hours, warehouseId)
    }
}
