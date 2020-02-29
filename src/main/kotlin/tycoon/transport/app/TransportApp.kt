package tycoon.transport.app

import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Shipment
import tycoon.transport.domain.ShipmentIds
import tycoon.transport.domain.Trip
import tycoon.transport.domain.Truck

class TransportApp(private val map: DistanceMap) {
    private val factory = Factory()
    private val factoryLocationId = LocationId("FACTORY")
    private val truck = Truck.on(Trip(Distance(0), factoryLocationId))
    private val shipmentIds = ShipmentIds()
    private var distanceDriven = Distance(0)

    fun ship(warehouseIds: List<String>) {
        factory.collectShipments(shipmentsFrom(warehouseIds))
        while (!factory.hasAllShipmentsDelivered()) {
            try {
                ship(factory.pickUpNextShipment())
            } catch (e: LocationUnknown) {
                throw RuntimeException("Unknown destination")
            }
        }
        distanceDriven = truck.distanceDriven()
    }

    private fun shipmentsFrom(warehouseIds: List<String>) =
        warehouseIds.map { Shipment(shipmentIds.next(), LocationId(it)) }

    private fun ship(shipment: Shipment) {
        val distance = map.distanceTo(shipment.destination)
        truck.startTrip(Trip(distance, shipment.destination))
        while (!truck.atDestination()) {
            truck.drive(Distance(1))
        }
        factory.shipmentDelivered(shipment)
        if (factory.hasShipmentsWaiting()) {
            truck.startTrip(Trip(distance, factoryLocationId))
            while (!truck.atDestination()) {
                truck.drive(Distance(1))
            }
        }
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
