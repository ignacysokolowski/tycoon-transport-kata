package tycoon.transport.domain

class DeliveryMap {
    fun distanceTo(warehouseId: String): Int {
        if (warehouseId != "A") {
            throw WarehouseUnknown()
        }
        return 5
    }
}
