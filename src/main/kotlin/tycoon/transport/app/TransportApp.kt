package tycoon.transport.app

import tycoon.transport.domain.DeliveryTracker
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.TransportArrivalNotifier
import tycoon.transport.domain.TransportMap
import tycoon.transport.domain.Warehouse
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoIdGenerator
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.MapRouter
import tycoon.transport.domain.carrier.Truck
import tycoon.transport.domain.time.StopWatch
import tycoon.transport.domain.time.TimeListener

class TransportApp : TimeListener {
    private val cargoIdGenerator = CargoIdGenerator()
    private val deliveryTracker = DeliveryTracker()
    private val factory = Factory(deliveryTracker)
    private val map = TransportMap(factory)
    private val truckRouter = MapRouter(factory.locationId, map)
    private val transportArrivalNotifier = TransportArrivalNotifier(map)
    private val stopWatch = StopWatch(this, timeLimit = 100)
    private var numberOfTrucks = 0
    private var trucks = mutableListOf<Truck>()

    fun setTrucks(number: Int) {
        numberOfTrucks = number
    }

    fun addWarehouse(locationId: LocationId, distance: Distance) {
        map.addLocation(Warehouse(locationId, deliveryTracker), distance)
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
        return stopWatch.timeElapsed()
    }

    private fun cargoesTo(warehouseIds: List<String>) =
        warehouseIds.map { Cargo(cargoIdGenerator.next(), LocationId(it)) }

    private fun ship(cargoes: List<Cargo>) {
        factory.produce(cargoes)
        parkTrucksAtTheFactory()
        stopWatch.countUntil { deliveryTracker.allCargoesDelivered() }
    }

    private fun parkTrucksAtTheFactory() = repeat(numberOfTrucks) { trucks.add(newTruck()) }

    private fun newTruck() = Truck.parked(truckRouter, transportArrivalNotifier)

    override fun tick() {
        trucks.forEach { it.drive(Distance(1)) }
    }
}
