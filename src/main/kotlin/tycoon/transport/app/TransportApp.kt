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
    private val shipmentIds = ShipmentIds()
    private var distanceDriven = Distance(0)
    private val warehouseController = WarehouseController(factory)

    fun ship(warehouseIds: List<String>) {
        if (warehouseIds.isEmpty()) {
            return
        }
        try {
            shipAll(shipmentsFrom(warehouseIds))
        } catch (e: LocationUnknown) {
            throw IllegalArgumentException("Unknown destination")
        }
    }

    private fun shipmentsFrom(warehouseIds: List<String>) =
        warehouseIds.map { Shipment(shipmentIds.next(), LocationId(it)) }

    private fun shipAll(shipments: List<Shipment>) {
        factory.collectShipments(shipments)
        val truck = Truck.on(Trip.inPlace(factory.locationId), this)
        while (!factory.hasAllShipmentsDelivered()) {
            truck.drive(Distance(1))
        }
        distanceDriven = truck.distanceDriven()
    }

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        if (locationId == factory.locationId) {
            arrivedAtFactory(truck)
        } else {
            arrivedAtWarehouse(truck)
        }
    }

    private fun arrivedAtFactory(truck: Truck) {
        val shipment = factory.pickUpNextShipment()
        truck.pickUp(shipment.id)
        truck.startTrip(tripFromFactoryTo(shipment.destination))
    }

    private fun tripFromFactoryTo(destination: LocationId) =
        Trip.between(factory.locationId, destination, map.distanceTo(destination))

    private fun arrivedAtWarehouse(truck: Truck) {
        warehouseController.transportArrived(truck)
    }

    fun totalDeliveryTime() = distanceDriven.hours
}
