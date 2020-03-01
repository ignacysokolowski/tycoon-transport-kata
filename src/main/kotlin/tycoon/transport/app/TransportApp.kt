package tycoon.transport.app

import tycoon.transport.domain.AllCargoPickedUp
import tycoon.transport.domain.Cargo
import tycoon.transport.domain.CargoIds
import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Router
import tycoon.transport.domain.Truck
import tycoon.transport.domain.TruckListener
import tycoon.transport.domain.WarehouseController

class TransportApp(map: DistanceMap) : TruckListener {
    private val cargoIds = CargoIds()
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
            shipAll(cargoTo(warehouseIds))
        } catch (e: LocationUnknown) {
            throw IllegalArgumentException("Unknown destination")
        }
    }

    private fun cargoTo(warehouseIds: List<String>) =
        warehouseIds.map { Cargo(cargoIds.next(), LocationId(it)) }

    private fun shipAll(cargo: List<Cargo>) {
        factory.produce(cargo)
        val trucks = createTrucks()
        while (!factory.hasAllCargoDelivered()) {
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
        val cargo = try {
            factory.pickUpNextCargo()
        } catch (e: AllCargoPickedUp) {
            return
        }
        truck.pickUp(cargo.id)
        truck.startTrip(router.tripTo(cargo.destination))
    }

    private fun arrivedAtWarehouse(truck: Truck) {
        warehouseController.transportArrived(truck)
    }

    fun totalDeliveryTime() = totalDeliveryTime
}
