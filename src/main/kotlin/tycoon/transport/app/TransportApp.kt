package tycoon.transport.app

import tycoon.transport.domain.AllShipmentsPickedUp
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
    private var numberOfTrucks = 0
    private var totalDeliveryTime = 0

    fun setTrucks(number: Int) {
        numberOfTrucks = number
    }

    fun ship(warehouseIds: List<String>) {
        if (warehouseIds.isEmpty()) {
            return
        }
        if (numberOfTrucks == 0) {
            throw IllegalStateException("No trucks at the factory")
        }
        try {
            shipAll(shipmentsTo(warehouseIds))
        } catch (e: LocationUnknown) {
            throw IllegalArgumentException("Unknown destination")
        }
    }

    private fun shipmentsTo(warehouseIds: List<String>) =
        warehouseIds.map { Shipment(shipmentIds.next(), LocationId(it)) }

    private fun shipAll(shipments: List<Shipment>) {
        factory.produce(shipments)
        val trucks = createTrucks()
        while (!factory.hasAllShipmentsDelivered()) {
            trucks.forEach { it.drive(Distance(1)) }
            totalDeliveryTime += 1
        }
    }

    private fun createTrucks() = (1..numberOfTrucks).map { newTruck() }

    private fun newTruck() = Truck.on(router.inPlaceTripAtOrigin(), this)

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        if (locationId == factory.locationId) {
            arrivedAtFactory(truck)
        } else {
            arrivedAtWarehouse(truck)
        }
    }

    private fun arrivedAtFactory(truck: Truck) {
        val shipment = try {
            factory.pickUpNextShipment()
        } catch (e: AllShipmentsPickedUp) {
            return
        }
        truck.pickUp(shipment.id)
        truck.startTrip(router.tripTo(shipment.destination))
    }

    private fun arrivedAtWarehouse(truck: Truck) {
        warehouseController.transportArrived(truck)
    }

    fun totalDeliveryTime() = totalDeliveryTime
}
