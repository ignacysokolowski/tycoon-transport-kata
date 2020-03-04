package tycoon.transport.app

import tycoon.transport.domain.Cargo
import tycoon.transport.domain.CargoIds
import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.Factory
import tycoon.transport.domain.Location
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.MapRouter
import tycoon.transport.domain.Truck
import tycoon.transport.domain.TruckListener
import tycoon.transport.domain.WarehouseController

class TransportApp(map: DistanceMap) : TruckListener {
    private val cargoIds = CargoIds()
    private val factory = Factory()
    private val truckRouter = MapRouter(factory.locationId, map)
    private val warehouseController = WarehouseController(factory)
    private var numberOfTrucks = 0
    private var totalDeliveryTime = 0

    fun setTrucks(number: Int) {
        numberOfTrucks = number
    }

    fun timeToDeliverCargoesToWarehouses(warehouseIds: List<String>): Int {
        if (warehouseIds.isEmpty()) {
            return 0
        }
        if (numberOfTrucks == 0) {
            throw IllegalStateException("No trucks at the factory")
        }
        try {
            ship(cargoesTo(warehouseIds))
        } catch (e: LocationUnknown) {
            throw IllegalArgumentException("Unknown destination")
        }
        return totalDeliveryTime
    }

    private fun cargoesTo(warehouseIds: List<String>) =
        warehouseIds.map { Cargo(cargoIds.next(), LocationId(it)) }

    private fun ship(cargoes: List<Cargo>) {
        factory.produce(cargoes)
        val trucks = parkTrucksAtTheFactory()
        while (!factory.hasAllCargoesDelivered()) {
            trucks.forEach { it.drive(Distance(1)) }
            totalDeliveryTime += 1
        }
    }

    private fun parkTrucksAtTheFactory() = (1..numberOfTrucks).map { newTruck() }

    private fun newTruck() = Truck.parked(truckRouter, this)

    override fun truckArrived(truck: Truck, locationId: LocationId) {
        val location: Location
        if (locationId == factory.locationId) {
            location = factory
            location.transportArrived(truck)
        } else {
            location = warehouseController
            location.transportArrived(truck)
        }
    }
}
