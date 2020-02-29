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

    @Test fun `has not driven yet`() {
        val truck = Truck.on(Trip(Distance(0), LocationId("A")))
        assertThat(truck.distanceDriven(), equalTo(Distance(0)))
    }

    @Test fun `not at the destination until has driven the whole trip distance`() {
        val truck = Truck.on(Trip(Distance(1), LocationId("A")))
        assertThat(truck.atDestination(), equalTo(false))
    }

    @Test fun `at the destination once has driven the whole trip distance`() {
        val truck = Truck.on(Trip(Distance(0), LocationId("A")))
        assertThat(truck.atDestination(), equalTo(true))
    }

    @Test fun `drives the trip distance`() {
        val truck = Truck.on(Trip(Distance(3), LocationId("A")))
        truck.drive(Distance(2))
        truck.drive(Distance(1))
        assertThat(truck.atDestination(), equalTo(true))
    }

    @Test fun `can not drive if already at destination`() {
        val truck = Truck.on(Trip(Distance(0), LocationId("A")))
        assertThrows<TruckAtDestination> {
            truck.drive(Distance(5))
        }
    }

    @Test fun `can start a new trip`() {
        val truck = Truck.on(Trip(Distance(0), LocationId("A")))
        truck.startTrip(Trip(Distance(1), LocationId("B")))
        truck.drive(Distance(1))
        assertThat(truck.atDestination(), equalTo(true))
    }

    @Test fun `records the distance it has driven`() {
        val truck = Truck.on(Trip(Distance(10), LocationId("A")))
        truck.drive(Distance(5))
        truck.drive(Distance(3))
        assertThat(truck.distanceDriven(), equalTo(Distance(8)))
    }
}
