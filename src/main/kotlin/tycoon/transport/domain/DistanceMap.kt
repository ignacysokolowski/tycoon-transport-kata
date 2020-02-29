package tycoon.transport.domain

class DistanceMap {
    private val distances = mutableMapOf<WarehouseId, Distance>()

    fun distanceTo(location: WarehouseId): Distance {
        return distances[location]
            ?: throw WarehouseUnknown()
    }

    fun addWarehouse(location: WarehouseId, distance: Distance) {
        distances[location] = distance
    }
}
