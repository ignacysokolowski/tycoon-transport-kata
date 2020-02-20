package tycoon.transport.domain

class DeliveryMap {
    fun distanceTo(warehouseId: String): Int {
        val distances = mapOf("A" to 5)
        if (warehouseId != "A") {
            throw WarehouseUnknown()
        }
        return distances[warehouseId]!!
    }
}
