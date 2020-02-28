package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Trip
import tycoon.transport.domain.Truck

class TruckTest {

    @Test fun `starts on a trip`() {
        val truck = Truck(Trip(Distance(2)))
        assertThat(truck.trip, equalTo(Trip(Distance(2))))
    }

    @Test fun `has not driven yet`() {
        val truck = Truck(Trip(Distance(0)))
        assertThat(truck.distanceDriven(), equalTo(Distance(0)))
    }

    @Test fun `can start a new trip`() {
        val truck = Truck(Trip(Distance(0)))
        truck.startTrip(Trip(Distance(2)))
        assertThat(truck.trip, equalTo(Trip(Distance(2))))
    }

    @Test fun `records the distance it has driven`() {
        val truck = Truck(Trip(Distance(10)))
        truck.drive(Distance(5))
        truck.drive(Distance(3))
        assertThat(truck.distanceDriven(), equalTo(Distance(8)))
    }
}
