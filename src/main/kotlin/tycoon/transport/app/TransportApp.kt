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

    fun ship(warehouseIds: List<String>) {
        factory.collectShipments(shipmentsFrom(warehouseIds))
        while (factory.hasShipmentsWaiting()) {
            ship(factory.pickUpNextShipment())
        }
        distanceDriven = truck.distanceDriven()
    }

    private fun shipmentsFrom(warehouseIds: List<String>) =
        warehouseIds.map { Shipment(WarehouseId(it)) }

    private fun ship(shipment: Shipment) {
        val distance = try {
            map.distanceTo(shipment.destination)
        } catch (e: WarehouseUnknown) {
            throw RuntimeException("Unknown destination")
        }
        truck.drive(distance)
        if (factory.hasShipmentsWaiting()) {
            truck.drive(distance)
        }
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
