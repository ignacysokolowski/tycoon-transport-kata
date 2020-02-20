package tycoon.transport.app

import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.WarehouseId
import tycoon.transport.domain.WarehouseUnknown

class TransportApp(private val map: DistanceMap) {
    private var distanceDriven = Distance(0)

    fun ship(warehouseIds: List<String>) {
        val warehouseId = warehouseIds[0]
        try {
            distanceDriven = map.distanceTo(WarehouseId(warehouseId))
        } catch (e: WarehouseUnknown) {
            throw RuntimeException("Unknown destination")
        }
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
