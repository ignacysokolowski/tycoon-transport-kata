package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.Distance
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.NoCargoCarried
import tycoon.transport.domain.Router
import tycoon.transport.domain.Trip
import tycoon.transport.domain.Truck

class FakeRouter : Router {
    private val origin = LocationId("A")

    override fun inPlaceTripAtOrigin() = Trip.inPlace(origin)
}

class TruckTest {
    private val router = FakeRouter()
    private val truckListener = TruckSpy()

    @Test fun `announces arrival at the initial location`() {
        val truck = Truck.at(LocationId("A"), truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `parks at the router's origin`() {
        val truck = Truck.parked(router, truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `announces arrival when placed on a zero-distance trip`() {
        val truck = Truck.at(LocationId("A"), truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `does not drive if already at destination`() {
        val truck = Truck.at(LocationId("A"), truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
        truck.drive(Distance(1))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `drives the whole distance to the trip destination`() {
        val truck = Truck.at(LocationId("A"), truckListener)
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
        val truck = Truck.at(LocationId("A"), truckListener)
        truck.startTrip(Trip.between(LocationId("A"), LocationId("B"), Distance(3)))
        truck.drive(Distance(2))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `drives back to the trip origin`() {
        val truck = Truck.at(LocationId("A"), truckListener)
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

    @Test fun `loads cargo`() {
        val truck = Truck.at(LocationId("A"), truckListener)
        truck.load(CargoId("1"))
        assertThat(truck.unload(), equalTo(CargoId("1")))
    }

    @Test fun `can not unload cargo if did not load`() {
        val truck = Truck.at(LocationId("A"), truckListener)
        assertThrows<NoCargoCarried> {
            truck.unload()
        }
    }

    @Test fun `has to load another cargo after unloading`() {
        val truck = Truck.at(LocationId("A"), truckListener)
        truck.load(CargoId("1"))
        truck.unload()
        assertThrows<NoCargoCarried> {
            truck.unload()
        }
    }
}
