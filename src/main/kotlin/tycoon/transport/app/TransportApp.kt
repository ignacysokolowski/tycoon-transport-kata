package tycoon.transport.app

import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.WarehouseId
import tycoon.transport.domain.WarehouseUnknown

class TransportApp(private val map: DistanceMap) {
    private var totalDeliveryTime = 0

    fun ship(warehouseId: String) {
        try {
            totalDeliveryTime = map.distanceTo(WarehouseId(warehouseId)).hours
        } catch (e: WarehouseUnknown) {
            throw RuntimeException("Unknown destination")
        }
    }

    fun totalDeliveryTime() = totalDeliveryTime
}
