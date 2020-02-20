package tycoon.transport.app

import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.Factory
import tycoon.transport.domain.Shipment
import tycoon.transport.domain.Truck
import tycoon.transport.domain.WarehouseId
import tycoon.transport.domain.WarehouseUnknown

class TransportApp(private val map: DistanceMap) {
    private val factory = Factory()
    private val truck = Truck()
    private var distanceDriven = Distance(0)

    fun ship(warehouseId: String) {
        factory.collectShipments(listOf(Shipment(WarehouseId(warehouseId))))
        val shipment = factory.pickUpNextShipment()
        val distance = try {
            map.distanceTo(shipment.destination)
        } catch (e: WarehouseUnknown) {
            throw RuntimeException("Unknown destination")
        }
        truck.drive(distance)
        distanceDriven = truck.distanceDriven()
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
