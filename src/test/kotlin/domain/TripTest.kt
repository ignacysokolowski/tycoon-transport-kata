package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Journey
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Trip

class TripTest {

    @Test fun `starts with a journey to the destination having full distance`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3))
        assertThat(trip.journey(), equalTo(Journey.to(LocationId("B"), Distance(3))))
    }
}
