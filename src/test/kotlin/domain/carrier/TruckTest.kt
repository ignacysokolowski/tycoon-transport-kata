package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.NoCargoCarried
import tycoon.transport.domain.carrier.Router
import tycoon.transport.domain.carrier.Trip
import tycoon.transport.domain.carrier.Truck

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
    private val truckListener = TransportSpy()

    @Test fun `announces arrival at the parking location`() {
        val truck = Truck.parked(router, truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TransportArrival(truck, LocationId("A")))))
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
                TransportArrival(truck, LocationId("A")),
                TransportArrival(truck, LocationId("B"))
            ))
        )
    }

    @Test fun `only notifies about actual arrivals`() {
        router.setTripDistance(Distance(3))
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        truck.drive(Distance(2))
        assertThat(truckListener.arrivals, equalTo(listOf(TransportArrival(truck, LocationId("A")))))
    }

    @Test fun `does not drive before loading cargo`() {
        val truck = Truck.parked(router, truckListener)
        truck.drive(Distance(1))
        assertThat(truckListener.arrivals, equalTo(listOf(TransportArrival(truck, LocationId("A")))))
    }

    @Test fun `does not move after arrival to the destination`() {
        router.setTripDistance(Distance(2))
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        truck.drive(Distance(1))
        truck.drive(Distance(1))
        truck.drive(Distance(1))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TransportArrival(truck, LocationId("A")),
                TransportArrival(truck, LocationId("B"))
            ))
        )
    }

    @Test fun `unloads cargo`() {
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        assertThat(truck.unload(), equalTo(CargoId("1")))
    }

    @Test fun `returns to the origin after unloading the cargo`() {
        router.setTripDistance(Distance(2))
        val truck = Truck.parked(router, truckListener)
        truck.load(Cargo(CargoId("1"), LocationId("B")))
        truck.drive(Distance(2))
        truck.unload()
        truck.drive(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TransportArrival(truck, LocationId("A")),
                TransportArrival(truck, LocationId("B")),
                TransportArrival(truck, LocationId("A"))
            ))
        )
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
