package tycoon.transport.domain

class DistanceMap {
    private val distances = mutableMapOf<WarehouseId, Distance>()

    fun distanceTo(warehouseId: WarehouseId): Distance {
        return distances[warehouseId]
            ?: throw WarehouseUnknown()
    }

    fun addWarehouse(warehouseId: WarehouseId, distance: Distance) {
        distances[warehouseId] = distance
    }
}
