package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Distance
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Trip
import tycoon.transport.domain.Truck
import tycoon.transport.domain.TruckAtDestination

class TruckTest {
    private val truckListener = TruckSpy()

    @Test fun `has not driven yet`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(0)))
        assertThat(truck.distanceDriven(), equalTo(Distance(0)))
    }

    @Test fun `not at the destination until has driven the whole trip distance`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(1)))
        assertThat(truck.atDestination(), equalTo(false))
    }

    @Test fun `at the destination once has driven the whole trip distance`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(0)))
        assertThat(truck.atDestination(), equalTo(true))
    }

    @Test fun `drives the trip distance`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(3)))
        truck.drive(Distance(2))
        truck.drive(Distance(1))
        assertThat(truck.atDestination(), equalTo(true))
    }

    @Test fun `notifies about arrivals`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(3)), truckListener)
        truck.drive(Distance(3))
        assertThat(truckListener.arrivals, equalTo(listOf(TruckArrival(truck, LocationId("A")))))
    }

    @Test fun `only notifies about actual arrivals`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(3)), truckListener)
        truck.drive(Distance(2))
        assertThat(truckListener.arrivals, equalTo(emptyList<TruckArrival>()))
    }

    @Test fun `can not drive if already at destination`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(0)))
        assertThrows<TruckAtDestination> {
            truck.drive(Distance(5))
        }
    }

    @Test fun `can start a new trip`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(0)))
        truck.startTrip(Trip.to(LocationId("B"), Distance(1)))
        truck.drive(Distance(1))
        assertThat(truck.atDestination(), equalTo(true))
    }

    @Test fun `records the distance it has driven`() {
        val truck = Truck.on(Trip.to(LocationId("A"), Distance(10)))
        truck.drive(Distance(5))
        truck.drive(Distance(3))
        assertThat(truck.distanceDriven(), equalTo(Distance(8)))
    }
}
