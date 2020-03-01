package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.Distance
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.NoCargoCarried
import tycoon.transport.domain.Trip
import tycoon.transport.domain.Truck

class TruckTest {
    private val truckListener = TruckSpy()

    @Test fun `not at the destination until has driven the whole journey distance`() {
        Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(1)), truckListener)
        assertThat(truckListener.arrivals, equalTo(emptyList<TruckArrival>()))
    }

    @Test fun `at the destination once has driven the whole journey distance`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(0)), truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("B")))))
    }

    @Test fun `drives the journey distance`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(3)), truckListener)
        truck.drive(Distance(2))
        truck.drive(Distance(1))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("B")))))
    }

    @Test fun `only notifies about actual arrivals`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(3)), truckListener)
        truck.drive(Distance(2))
        assertThat(truckListener.arrivals, equalTo(emptyList<TruckArrival>()))
    }

    @Test fun `does not drive if already at destination`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(0)), truckListener)
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("B")))))
        truck.drive(Distance(1))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("B")))))
    }

    @Test fun `can start a new trip`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(0)), truckListener)
        truck.startTrip(Trip.between(LocationId("B"), LocationId("A"), Distance(1)))
        truck.drive(Distance(1))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TruckArrival(truck, LocationId("B")),
                TruckArrival(truck, LocationId("A"))
            ))
        )
    }

    @Test fun `drives back to the trip origin`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(2)), truckListener)
        truck.drive(Distance(2))
        truck.goBack()
        truck.drive(Distance(2))
        assertThat(
            truckListener.arrivals,
            equalTo(listOf(
                TruckArrival(truck, LocationId("B")),
                TruckArrival(truck, LocationId("A"))
            ))
        )
    }

    @Test fun `picks up cargo`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(10)), truckListener)
        truck.pickUp(CargoId("1"))
        assertThat(truck.dropOff(), equalTo(CargoId("1")))
    }

    @Test fun `can not drop off cargo if did not pick up`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(10)), truckListener)
        assertThrows<NoCargoCarried> {
            truck.dropOff()
        }
    }

    @Test fun `has to pick up another cargo after dropping off`() {
        val truck = Truck.on(Trip.between(LocationId("A"), LocationId("B"), Distance(10)), truckListener)
        truck.pickUp(CargoId("1"))
        truck.dropOff()
        assertThrows<NoCargoCarried> {
            truck.dropOff()
        }
    }
}
