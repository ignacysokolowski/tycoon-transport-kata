package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Cargo
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.Distance
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.NoCargoCarried
import tycoon.transport.domain.Router
import tycoon.transport.domain.Trip
import tycoon.transport.domain.Truck

class FakeRouter : Router {
    private val origin = LocationId("A")
    private var tripDistance = Distance(0)

    fun setTripDistance(distance: Distance) {
        this.tripDistance = distance
    }

    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)
    override fun tripTo(destination: LocationId) = Trip.between(origin, destination, tripDistance)
}

class TruckTest {
    private val router = FakeRouter()
    private val truckListener = TruckSpy()

    @Test fun `announces arrival at the parking location`() {
        val truck = Truck.parked(router, truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `does not drive if already at destination`() {
        val truck = Truck.parked(router, truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
        truck.drive(Distance(1))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `drives the whole distance to the trip destination`() {
        val truck = Truck.parked(router, truckListener)
        truck.startTrip(Trip.between(LocationId("A"), LocationId("B"), Distance(3)))
        truck.drive(Distance(1))
        truck.drive(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TruckArrival(truck, LocationId("A")),
                TruckArrival(truck, LocationId("B"))
            ))
        )
    }

    @Test fun `only notifies about actual arrivals`() {
        val truck = Truck.parked(router, truckListener)
        truck.startTrip(Trip.between(LocationId("A"), LocationId("B"), Distance(3)))
        truck.drive(Distance(2))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `drives back to the trip origin`() {
        val truck = Truck.parked(router, truckListener)
        truck.startTrip(Trip.between(LocationId("A"), LocationId("B"), Distance(2)))
        truck.drive(Distance(2))
        truck.goBack()
        truck.drive(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TruckArrival(truck, LocationId("A")),
                TruckArrival(truck, LocationId("B")),
                TruckArrival(truck, LocationId("A"))
            ))
        )
    }

    @Test fun `loads cargo and drives to its destination`() {
        router.setTripDistance(Distance(3))
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        truck.drive(Distance(1))
        truck.drive(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TruckArrival(truck, LocationId("A")),
                TruckArrival(truck, LocationId("B"))
            ))
        )
    }

    @Test fun `unloads cargo`() {
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        assertThat(truck.unload(), equalTo(CargoId("1")))
    }

    @Test fun `can not unload cargo if did not load`() {
        val truck = Truck.parked(router, truckListener)
        assertThrows<NoCargoCarried> {
            truck.unload()
        }
    }

    @Test fun `has to load another cargo after unloading`() {
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        truck.unload()
        assertThrows<NoCargoCarried> {
            truck.unload()
        }
    }
}
