package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Location
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.NoCargoCarried
import tycoon.transport.domain.carrier.Trip
import tycoon.transport.domain.carrier.TripPlanner
import tycoon.transport.domain.carrier.Truck

class FakeTripPlanner : TripPlanner {
    private val origin = Location("A")
    private var tripDistance = Distance(0)

    fun setTripDistance(distance: Distance) {
        this.tripDistance = distance
    }

    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)
    override fun tripTo(destination: Location) = Trip.between(origin, destination, tripDistance)
}

class TruckTest {
    private val tripPlanner = FakeTripPlanner()
    private val truckListener = TransportSpy()

    @Test fun `announces arrival at the parking location`() {
        val truck = Truck.parked(tripPlanner, truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TransportArrival(truck, Location("A")))))
    }

    @Test fun `loads cargo and moves to its destination`() {
        tripPlanner.setTripDistance(Distance(3))
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.load(Cargo(CargoId("1"), Location("B")))
        truck.move(Distance(1))
        truck.move(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TransportArrival(truck, Location("A")),
                TransportArrival(truck, Location("B"))
            ))
        )
    }

    @Test fun `only notifies about actual arrivals`() {
        tripPlanner.setTripDistance(Distance(3))
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.load(Cargo(CargoId("1"), Location("B")))
        truck.move(Distance(2))
        assertThat(truckListener.arrivals, equalTo(listOf(TransportArrival(truck, Location("A")))))
    }

    @Test fun `does not move before loading cargo`() {
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.move(Distance(1))
        assertThat(truckListener.arrivals, equalTo(listOf(TransportArrival(truck, Location("A")))))
    }

    @Test fun `does not move after arrival to the destination`() {
        tripPlanner.setTripDistance(Distance(2))
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.load(Cargo(CargoId("1"), Location("B")))
        truck.move(Distance(1))
        truck.move(Distance(1))
        truck.move(Distance(1))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TransportArrival(truck, Location("A")),
                TransportArrival(truck, Location("B"))
            ))
        )
    }

    @Test fun `unloads cargo`() {
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.load(Cargo(CargoId("1"), Location("B")))
        assertThat(truck.unload(), equalTo(CargoId("1")))
    }

    @Test fun `returns to the origin after unloading the cargo`() {
        tripPlanner.setTripDistance(Distance(2))
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.load(Cargo(CargoId("1"), Location("B")))
        truck.move(Distance(2))
        truck.unload()
        truck.move(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TransportArrival(truck, Location("A")),
                TransportArrival(truck, Location("B")),
                TransportArrival(truck, Location("A"))
            ))
        )
    }

    @Test fun `can not unload cargo if did not load`() {
        val truck = Truck.parked(tripPlanner, truckListener)
        assertThrows<NoCargoCarried> {
            truck.unload()
        }
    }

    @Test fun `has to load another cargo after unloading`() {
        val truck = Truck.parked(tripPlanner, truckListener)
        truck.load(Cargo(CargoId("1"), Location("B")))
        truck.unload()
        assertThrows<NoCargoCarried> {
            truck.unload()
        }
    }
}
