package tycoon.transport.domain

class DistanceMap {
    private val distances = mutableMapOf<WarehouseId, Int>()

    fun distanceTo(warehouseId: WarehouseId): Int {
        return distances[warehouseId]
            ?: throw WarehouseUnknown()
    }

    fun addWarehouse(warehouseId: WarehouseId, distance: Distance) {
        distances[warehouseId] = distance.hours
    }
}
