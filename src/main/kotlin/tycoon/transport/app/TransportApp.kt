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
        if (warehouseIds.isEmpty()) {
            return
        }
        shipAll(shipmentsFrom(warehouseIds))
    }

    private fun shipmentsFrom(warehouseIds: List<String>) =
        warehouseIds.map { Shipment(shipmentIds.next(), LocationId(it)) }

    private fun shipAll(shipments: List<Shipment>) {
        factory.collectShipments(shipments)
        try {
            shipNext(truck)
        } catch (e: LocationUnknown) {
            throw IllegalArgumentException("Unknown destination")
        }
        while (!factory.hasAllShipmentsDelivered()) {
            truck.drive(Distance(1))
        }
        distanceDriven = truck.distanceDriven()
    }

    private fun shipNext(truck: Truck) {
        val shipment = factory.pickUpNextShipment()
        val distance = map.distanceTo(shipment.destination)
        truck.pickUp(shipment.id)
        truck.startTrip(Trip.to(shipment.destination, distance))
    }

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        if (locationId == factoryLocationId) {
            arrivedAtFactory(truck)
        } else {
            arrivedAtWarehouse(truck, locationId)
        }
    }

    private fun arrivedAtFactory(truck: Truck) {
        if (factory.hasShipmentsWaiting()) {
            shipNext(truck)
        }
    }

    private fun arrivedAtWarehouse(truck: Truck, locationId: LocationId) {
        factory.shipmentDelivered(truck.dropOff())
        truck.startTrip(Trip.to(factoryLocationId, map.distanceTo(locationId)))
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
