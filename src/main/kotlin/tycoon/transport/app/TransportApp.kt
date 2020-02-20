package tycoon.transport.app

import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.Truck
import tycoon.transport.domain.WarehouseId
import tycoon.transport.domain.WarehouseUnknown

class TransportApp(private val map: DistanceMap) {
    private var distanceDriven = Distance(0)

    fun ship(warehouseId: String) {
        val distance = try {
            map.distanceTo(WarehouseId(warehouseId))
        } catch (e: WarehouseUnknown) {
            throw RuntimeException("Unknown destination")
        }
        val truck = Truck()
        truck.drive(distance)
        distanceDriven = truck.distanceDriven()
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
