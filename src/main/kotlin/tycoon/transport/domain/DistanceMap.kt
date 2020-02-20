package tycoon.transport.domain

class DistanceMap {
    private val distances = mutableMapOf<WarehouseId, Distance>()

    fun distanceTo(warehouseId: WarehouseId): Int {
        return distances[warehouseId]?.hours
            ?: throw WarehouseUnknown()
    }

    fun addWarehouse(warehouseId: WarehouseId, distance: Distance) {
        distances[warehouseId] = distance
    }
}
