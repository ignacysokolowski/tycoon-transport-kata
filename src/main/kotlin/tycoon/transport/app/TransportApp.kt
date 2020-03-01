package tycoon.transport.app

import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Router
import tycoon.transport.domain.Shipment
import tycoon.transport.domain.ShipmentIds
import tycoon.transport.domain.Truck
import tycoon.transport.domain.TruckListener

class TransportApp(map: DistanceMap) : TruckListener {
    private val shipmentIds = ShipmentIds()
    private val factory = Factory()
    private val router = Router(factory.locationId, map)
    private val warehouseController = WarehouseController(factory)
    private var totalDeliveryTime = 0

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
        val trucks = listOf(newTruck())
        while (!factory.hasAllShipmentsDelivered()) {
            trucks.forEach { it.drive(Distance(1)) }
            totalDeliveryTime += 1
        }
    }

    private fun newTruck() = Truck.on(router.inPlaceTripAtOrigin(), this)

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
        truck.startTrip(router.tripTo(shipment.destination))
    }

    private fun arrivedAtWarehouse(truck: Truck) {
        warehouseController.transportArrived(truck)
    }

    fun totalDeliveryTime() = totalDeliveryTime
}
