package tycoon.transport.domain

class DeliveryMap {
    private val distances = mapOf("A" to 5)

    fun distanceTo(warehouseId: String): Int {
        if (warehouseId != "A") {
            throw WarehouseUnknown()
        }
        return distances[warehouseId]!!
    }
}
