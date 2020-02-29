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
import tycoon.transport.domain.TruckListener

class TransportApp(private val map: DistanceMap) : TruckListener {
    private val factory = Factory()
    private val factoryLocationId = LocationId("FACTORY")
    private val truck = Truck.on(Trip.to(factoryLocationId, Distance(0)), this)
    private val shipmentIds = ShipmentIds()
    private var distanceDriven = Distance(0)

    fun ship(warehouseIds: List<String>) {
        factory.collectShipments(shipmentsFrom(warehouseIds))
        try {
            shipNext()
        } catch (e: LocationUnknown) {
            throw RuntimeException("Unknown destination")
        }
        while (!factory.hasAllShipmentsDelivered()) {
            truck.drive(Distance(1))
        }
        distanceDriven = truck.distanceDriven()
    }

    private fun shipmentsFrom(warehouseIds: List<String>) =
        warehouseIds.map { Shipment(shipmentIds.next(), LocationId(it)) }

    private fun shipNext() {
        ship(factory.pickUpNextShipment())
    }

    private fun ship(shipment: Shipment) {
        val distance = map.distanceTo(shipment.destination)
        truck.pickUp(shipment.id)
        truck.startTrip(Trip.to(shipment.destination, distance))
    }

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        if (locationId != factoryLocationId) {
            factory.shipmentDelivered(truck.dropOff())
            truck.startTrip(Trip.to(factoryLocationId, map.distanceTo(locationId)))
        } else {
            if (factory.hasShipmentsWaiting()) {
                shipNext()
            }
        }
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
